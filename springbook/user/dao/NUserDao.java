package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import springbook.user.domain.User;

public class NUserDao extends UserDao {

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/mysql", "root", "admin");
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        NUserDao dao = new NUserDao();

        dao.deleteAll();

        User user = new User();
        user.setId("uj");
        user.setName("이의정");
        user.setPassword("비밀번호");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }
}
