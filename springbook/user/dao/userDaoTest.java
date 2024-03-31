package springbook.user.dao;

import java.sql.SQLException;

import springbook.user.domain.User;

public class userDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ConnectionMaker connectionMaker = new DConnectionMaker(); // UserDao가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 만든다.

        UserDao dao = new UserDao(connectionMaker);
        /*
         * 1. UserDao 생성
         * 2. 사용할 ConnectionMaker 타입의 오브젝트 제공
         *  결국 두 오브젝트 사이의 의존관계 설정 효과
        */

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
