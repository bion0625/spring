package springbook.user.dao;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.domain.User;

public class UserDaoTest {

    public static void main(String[] args) {
        JUnitCore.main("springbook.user.dao.UserDaoTest");
    }

    @Test // JUnit에게 테스트용 메소드임을 알려준다.
    public void addAndGet() throws SQLException { // JUnit 메소드는 반드시 public으로 선언되어야 한다.
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao dao = context.getBean("specialUserDao", UserDao.class);

        dao.deleteAll();

        User user = new User();
        user.setId("uj");
        user.setName("이의정");
        user.setPassword("비밀번호");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());

        assertThat(user2.getName(), is(user.getName()));
        assertThat(user2.getPassword(), is(user.getPassword()));
    }
}
