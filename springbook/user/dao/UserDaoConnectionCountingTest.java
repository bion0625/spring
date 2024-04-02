package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springbook.user.domain.User;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        UserDao userDao = context.getBean("specialUserDao", UserDao.class);

        userDao.deleteAll();

        User user = new User();
        user.setId("countingId");
        user.setName("countingName");
        user.setPassword("Password");
        userDao.add(user);

        User getUser = userDao.get("countingId");
        System.out.println(getUser.getId());
        System.out.println(getUser.getName());
        System.out.println(getUser.getPassword());


        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter : " + ccm.getCounter());
    }
}
