package springbook.learningtest.spring.tx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import springbook.learningtest.spring.hibernate.MemberDaoByTemplate;
import springbook.learningtest.spring.jdbc.MemberDao;
import springbook.learningtest.spring.jpa.Member;
import springbook.learningtest.spring.jpa.MemberTemplateDao;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springbook/learningtest/spring/tx/jpa-context.xml")
public class TxTest {

    @Autowired
    MemberTemplateDao memberTemplateDao;

    @Before
    public void setUp() {
        ApplicationContext ac = new GenericXmlApplicationContext("springbook/learningtest/spring/tx/jdbc-context.xml");
        MemberDao memberDao = ac.getBean(MemberDao.class);

        memberDao.deleteAll();

        ApplicationContext hibernateAc = new GenericXmlApplicationContext("springbook/learningtest/spring/tx/hibernate-context.xml");
        MemberDaoByTemplate hibernateMemberDao = hibernateAc.getBean(MemberDaoByTemplate.class);

        List<springbook.learningtest.spring.hibernate.Member> memberAll = hibernateMemberDao.findMemberAll();
        for (springbook.learningtest.spring.hibernate.Member member : memberAll) {
            hibernateMemberDao.deleteMember(member);
        }
    }

    @Test
    public void jdbcTest() {
        ApplicationContext ac = new GenericXmlApplicationContext("springbook/learningtest/spring/tx/jdbc-context.xml");
        MemberDao memberDao = ac.getBean(MemberDao.class);

        memberDao.deleteAll();
        assertThat(memberDao.findAllCount(), is(0));

        memberDao.save("test", 1.1);
        Map<String, Object> test = memberDao.findByName("test");
        assertThat(test.get("name"), is("test"));
        assertThat(test.get("point"), is(1.1F));
    }

    /*
     * VM 옵션 (InstrumentationLoadTimeWeaver 관련)
     * -javaagent:springbook/lib/spring-instrument-3.0.3.RELEASE.jar
     * */
    @Test
    @Transactional
    public void jpaTest() {
        memberTemplateDao.getJpaTemplate().persist(new Member(1, "jpaTest", 1.2));

        Member member = memberTemplateDao.getJpaTemplate().find(Member.class, 1);

        assertThat(member.getId(), is(1));
        assertThat(member.getName(), is("jpaTest"));
        assertThat(member.getPoint(), is(1.2));
    }

    @Test
    public void hibernateTest() {
        ApplicationContext ac = new GenericXmlApplicationContext("springbook/learningtest/spring/tx/hibernate-context.xml");
        MemberDaoByTemplate memberDao = ac.getBean(MemberDaoByTemplate.class);

        memberDao.addMember(new springbook.learningtest.spring.hibernate.Member(1, "testHibernate", 1.3));
        springbook.learningtest.spring.hibernate.Member member = memberDao.findMemberById(1);

        assertThat(member.getId(), is(1));
        assertThat(member.getName(), is("testHibernate"));
        assertThat(member.getPoint(), is(1.3));
    }

    @Test
    public void doubleDatabaseContextTest() {
        ApplicationContext ac = new GenericXmlApplicationContext("springbook/learningtest/spring/tx/doubleDatabase-context.xml");
        MemberDaoByJdbc1 memberDao1 = ac.getBean(MemberDaoByJdbc1.class);
        MemberDaoByJdbc2 memberDao2 = ac.getBean(MemberDaoByJdbc2.class);

        memberDao2.deleteAll();
        memberDao1.save("jdbc1Test", 3.1);
        String name = memberDao2.findNameById(1);
        assertThat(name, is("jdbc1Test"));
    }
}
