package springbook.learningtest.spring.ibatis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.learningtest.spring.jdbc.Member;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class IbatisTest {

    @Autowired
    MemberDao memberDao;

    @Before
    public void setUp() {
        memberDao.deleteAll();
    }

    @Test
    public void insertTest() {
        memberDao.insert(new Member(1, "member1", 1.0));
        Member member = memberDao.findById(1);
        compareMember(member, 1, "member1", 1.0);
    }

    @Test
    public void findAllTest() {
        memberDao.insert(new Member(1, "member1", 1.0));
        memberDao.insert(new Member(2, "member2", 2.0));
        memberDao.insert(new Member(3, "member3", 3.0));
        memberDao.insert(new Member(4, "member4", 4.0));
        memberDao.insert(new Member(5, "member5", 5.0));

        List<Member> all = memberDao.findAll();
        compareMember(all.get(0), 1, "member1", 1.0);
        compareMember(all.get(1), 2, "member2", 2.0);
        compareMember(all.get(2), 3, "member3", 3.0);
        compareMember(all.get(3), 4, "member4", 4.0);
        compareMember(all.get(4), 5, "member5", 5.0);
    }

    @Test
    public void queryForMapTest() {
        memberDao.insert(new Member(1, "member1", 1.0));
        memberDao.insert(new Member(2, "member2", 2.0));
        memberDao.insert(new Member(3, "member3", 3.0));
        memberDao.insert(new Member(4, "member4", 4.0));
        memberDao.insert(new Member(5, "member5", 5.0));

        Map<Integer, Member> all = memberDao.searchMemberByName("member");
        compareMember(all.get(1), 1, "member1", 1.0);
        compareMember(all.get(2), 2, "member2", 2.0);
        compareMember(all.get(3), 3, "member3", 3.0);
        compareMember(all.get(4), 4, "member4", 4.0);
        compareMember(all.get(5), 5, "member5", 5.0);

    }

    @Test
    public void rowHandlerTest() {
        memberDao.insert(new Member(1, "member1", 1.0));
        memberDao.insert(new Member(2, "member2", 2.0));
        memberDao.insert(new Member(3, "member3", 3.0));
        memberDao.insert(new Member(4, "member4", 4.0));
        memberDao.insert(new Member(5, "member5", 5.0));

        memberDao.searchMemberByPoint(3.0);
    }

    private void compareMember(Member member, int expectId, String expectName, double expectPoint) {
        assertThat(member.getId(), is(expectId));
        assertThat(member.getName(), is(expectName));
        assertThat(member.getPoint(), is(expectPoint));
    }
}
