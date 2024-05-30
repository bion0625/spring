package springbook.learningtest.spring.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class jdbcTest {
    @Autowired
    MemberDao memberDao;

    @Autowired
    RegisterDao registerDao;

    @Test
    public void simpleJdbcTemplateTest() {
        memberDao.deleteAll();
        assertThat(memberDao.findAllCount(), is(0));

        memberDao.testSaveTwo();
        assertThat(memberDao.findAllCount(), is(2));
        assertThat(memberDao.findCountThanPoint(3), is(1));

        assertThat(memberDao.findNameById("0"), is(nullValue()));
        assertThat(memberDao.findNameById("1"), is("Spring"));
        assertThat(memberDao.findNameById("2"), is("Spring2"));

        Member member = memberDao.findById("1");
        assertThat(member.getId(), is("1"));
        assertThat(member.getName(), is("Spring"));
        assertThat(member.getPoint(), is(3.5));

        List<Member> all = memberDao.findAllGreateThanPoint(0);
        assertThat(all.size(), is(2));
        assertThat(all.get(0).getId(), is("1"));
        assertThat(all.get(0).getName(), is("Spring"));
        assertThat(all.get(0).getPoint(), is(3.5));
        assertThat(all.get(1).getId(), is("2"));
        assertThat(all.get(1).getName(), is("Spring2"));
        assertThat(all.get(1).getPoint(), is(3.0));

        Map<String, Object> map = memberDao.findByName("Spring2");
        assertThat(map.get("id"), is("2"));
        assertThat(map.get("name"), is("Spring2"));
        assertThat(map.get("point"), is(3.0F));

        List<Map<String, Object>> list = memberDao.searchByName("Spring");
        assertThat(list.get(0).get("id"), is("1"));
        assertThat(list.get(0).get("name"), is("Spring"));
        assertThat(list.get(0).get("point"), is(3.5F));
        assertThat(list.get(1).get("id"), is("2"));
        assertThat(list.get(1).get("name"), is("Spring2"));
        assertThat(list.get(1).get("point"), is(3.0F));

        memberDao.updateAllPoint(new String[] {"Spring", "Spring2"}, new double[] {1.0, 1.2});
        list = memberDao.searchByName("Spring");
        assertThat(list.get(0).get("id"), is("1"));
        assertThat(list.get(0).get("name"), is("Spring"));
        assertThat(list.get(0).get("point"), is(1.0F));
        assertThat(list.get(1).get("id"), is("2"));
        assertThat(list.get(1).get("name"), is("Spring2"));
        assertThat(list.get(1).get("point"), is(1.2F));

        memberDao.updataAllNameById(new String[] {"1", "2"}, new String[] {"Double", "Double2"});
        list = memberDao.searchByName("Double");
        assertThat(list.size(), is(2));
        assertThat(list.get(0).get("id"), is("1"));
        assertThat(list.get(0).get("name"), is("Double"));
        assertThat(list.get(0).get("point"), is(1.0F));
        assertThat(list.get(1).get("id"), is("2"));
        assertThat(list.get(1).get("name"), is("Double2"));
        assertThat(list.get(1).get("point"), is(1.2F));
    }

    @Test
    public void simpleJdbcInsertTest() {
        memberDao.deleteAll();

        memberDao.save("testName", 0.1);
        Map<String, Object> map = memberDao.findByName("testName");
        String testId = (String) map.get("id");
        assertThat(map.get("name"), is("testName"));
        assertThat(map.get("point"), is(0.1F));

        Integer id = registerDao.save("Spring");
        assertThat(registerDao.findNameById(id), is("Spring"));

        assertThat(memberDao.callNameById(testId), is("testName"));


    }
}
