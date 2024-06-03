package springbook.learningtest.spring.hibernate;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class HibernateTest {
    @Autowired
    SessionFactory sf;

    @Autowired
    MemberDaoByTemplate memberDao;

    @Autowired
    MemberDaoByJustSession memberDaoByJustSession;

    @Before
    public void setUp() {
        List<Member> memberAll = memberDao.findMemberAll();
        for (Member member : memberAll) {
            memberDao.deleteMember(member);
        }
    }

    @Test
    public void sessionFactoryTest() {
        assertThat(sf, is(notNullValue()));
    }

    @Test
    public void addMemberTest() {
        Member member = new Member(1, "Spring", 1.1);
        memberDao.addMember(member);
        Member findMember = memberDao.findMemberById(1);
        compare(member, findMember);
    }

    @Test
    public void findAllTest() {
        memberDao.addMember(new Member(1, "Spring", 1.1));
        memberDao.addMember(new Member(2, "Spring1", 1.1));
        memberDao.addMember(new Member(3, "Sprin2", 1.1));
        assertThat(memberDao.findMemberAll().size(), is(3));
    }

    @Test
    public void deleteTest() {
        Member member = new Member(1, "Spring", 1.1);
        memberDao.addMember(member);

        Member findMember = memberDao.findMemberById(member.getId());
        compare(member, findMember);

        memberDao.deleteMember(findMember);
        assertThat(memberDao.findMemberById(member.getId()), is(nullValue()));
    }

    @Test
    public void getAllCountTest() {
        memberDao.addMember(new Member(1, "Spring", 1.1));
        assertThat(memberDao.getAllCount(), is(1L));

        memberDao.addMember(new Member(2, "Spring1", 1.1));
        assertThat(memberDao.getAllCount(), is(2L));

        memberDao.addMember(new Member(3, "Sprin2", 1.1));
        assertThat(memberDao.getAllCount(), is(3L));

    }

    @Test
    public void addMemberBySessionTest() {
        Member member = new Member(1, "Spring", 1.1);
        memberDaoByJustSession.addMember(member);
        Member findMember = memberDao.findMemberById(1);
        compare(member, findMember);
    }

    private void compare(Member member, Member expectedMember) {
        assertThat(member.getId(), is(expectedMember.getId()));
        assertThat(member.getName(), is(expectedMember.getName()));
        assertThat(member.getPoint(), is(expectedMember.getPoint()));
    }
}
