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

import java.util.ArrayList;
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

    @Test
    public void memberServiceTest() {
        List<springbook.learningtest.spring.jdbc.Member> members = new ArrayList<>();
        members.add(new springbook.learningtest.spring.jdbc.Member(1, "test1", 1.1));
        members.add(new springbook.learningtest.spring.jdbc.Member(2, "test2", 2.1));
        members.add(new springbook.learningtest.spring.jdbc.Member(3, "test3", 3.1));

        ApplicationContext ac = new GenericXmlApplicationContext("springbook/learningtest/spring/tx/jdbc-context.xml");
        MemberService memberService = ac.getBean(MemberService.class);
        memberService.addMembers(members);

        MemberDao memberDao = ac.getBean(MemberDao.class);

        assertThat(memberDao.findAllCount(), is(3));

        springbook.learningtest.spring.jdbc.Member member1 = memberDao.findById("1");
        springbook.learningtest.spring.jdbc.Member member2 = memberDao.findById("2");
        springbook.learningtest.spring.jdbc.Member member3 = memberDao.findById("3");

        assertThat(member1.getId(), is(1));
        assertThat(member1.getName(), is("test1"));
        assertThat(member1.getPoint(), is(1.1));

        assertThat(member2.getId(), is(2));
        assertThat(member2.getName(), is("test2"));
        assertThat(member2.getPoint(), is(2.1));

        assertThat(member3.getId(), is(3));
        assertThat(member3.getName(), is("test3"));
        assertThat(member3.getPoint(), is(3.1));
    }

    @Test
    public void txAndAopTest() {
        ApplicationContext ac = new GenericXmlApplicationContext("springbook/learningtest/spring/tx/txAndAop-context.xml");
        springbook.learningtest.spring.tx.MemberDao memberDao = ac.getBean(springbook.learningtest.spring.tx.MemberDao.class);
        memberDao.deleteAll();
        assertThat(memberDao.count(), is(0L));

        List<springbook.learningtest.spring.jdbc.Member> members = new ArrayList<>();
        members.add(new springbook.learningtest.spring.jdbc.Member(1, "test1", 1.1));
        members.add(new springbook.learningtest.spring.jdbc.Member(2, "test2", 2.1));
        members.add(new springbook.learningtest.spring.jdbc.Member(3, "test3", 3.1));
        memberDao.add(members);

        assertThat(memberDao.count(), is(3L));
    }
}
