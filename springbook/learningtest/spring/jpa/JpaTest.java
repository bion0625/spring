package springbook.learningtest.spring.jpa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/*
* VM 옵션 (InstrumentationLoadTimeWeaver 관련)
* -javaagent:springbook/lib/spring-instrument-3.0.3.RELEASE.jar
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class JpaTest {
    @PersistenceContext
    EntityManager emf;

    @Autowired
    MemberTemplateDao memberTemplateDao;

    @Autowired
    MemberDao memberDao;

    @Before
    public void setUp() {
        emf.createQuery("delete from Member").executeUpdate();
    }

    @Test
    public void entityManagerTest() {
        Member member = new Member();
        member.setId(1);
        member.setName("jpaTest");
        member.setPoint(1.1);
        emf.persist(member);
        List<Member> members = emf.createQuery("select m from Member m", Member.class).getResultList();
        assertThat(members.size(), is(1));
        compare(members.get(0), 1, "jpaTest", 1.1);
    }

    @Test
    public void templateExecuteTest() {
        Member m = new Member(1, "Spring", 8.9);
        memberTemplateDao.getJpaTemplate().persist(m);

        List<Member> ms = memberTemplateDao.getJpaTemplate().execute(new JpaCallback<List<Member>>() {
            @Override
            public List<Member> doInJpa(EntityManager entityManager) throws PersistenceException {
                return entityManager.createQuery("select m from Member m").getResultList();
            }
        });

        assertThat(ms.size(), is(1));
        compare(ms.get(0), 1, "Spring", 8.9);
    }

    @Test
    public void templateTest() {
        Member m = new Member(1, "Spring", 8.9);
        memberTemplateDao.getJpaTemplate().persist(m);
        Member m2 = memberTemplateDao.getJpaTemplate().find(Member.class, 1);

        compare(m, m2);
    }

    @Test
    public void persistenceContextTest() {
        Member m = new Member(1, "Spring", 8.9);
        memberDao.addMember(m);
        Member m2 = memberTemplateDao.getJpaTemplate().find(Member.class, 1);

        compare(m, m2);
    }

    private void compare(Member member, Member expectedMember) {
        assertThat(member.getId(), is(expectedMember.getId()));
        assertThat(member.getName(), is(expectedMember.getName()));
        assertThat(member.getPoint(), is(expectedMember.getPoint()));
    }

    private void compare(Member member, int expectedId, String expectedName, double expectedPoint) {
        assertThat(member.getId(), is(expectedId));
        assertThat(member.getName(), is(expectedName));
        assertThat(member.getPoint(), is(expectedPoint));
    }
}
