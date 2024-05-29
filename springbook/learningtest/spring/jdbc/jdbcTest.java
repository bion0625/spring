package springbook.learningtest.spring.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class jdbcTest {
    @Autowired
    MemberDao memberDao;

    @Test
    public void getAll() {
        memberDao.deleteAll();
        assertThat(memberDao.findAllCount(), is(0));

        memberDao.testSaveOne();
        assertThat(memberDao.findAllCount(), is(1));
    }
}
