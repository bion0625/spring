package springbook.learningtest.spring.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/*
* VM 옵션
* -javaagent:springbook/lib/spring-instrument-3.0.3.RELEASE.jar
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class JpaTest {
    @PersistenceContext
    EntityManager emf;

//    @Rollback(false)
    @Test
    public void test() {
        emf.createQuery("delete from Member").executeUpdate();
        Member member = new Member();
        member.setId(1);
        member.setName("jpaTest");
        member.setPoint(1.1);
        emf.persist(member);
        List<Member> members = emf.createQuery("select m from Member m", Member.class).getResultList();
        assertThat(members.size(), is(1));
        assertThat(members.get(0).getId(), is(1));
        assertThat(members.get(0).getName(), is("jpaTest"));
        assertThat(members.get(0).getPoint(), is(1.1));
    }
}
