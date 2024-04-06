package springbook.user.dao;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import springbook.user.domain.User;

public class UserDaoTest {

    UserDao dao; // @Autowired가 없다.

    User user1;
    User user2;
    User user3;

    public static void main(String[] args) {
        JUnitCore.main("springbook.user.dao.UserDaoTest");
    }

    @Before
    public void setUp() {

        /*
         * 오브젝트 생성, 관계 설정 등을 모두 직접 해준다.
        */
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource(
            "jdbc:mysql://localhost:3306/testdb", "root", "admin", true
        );

        dao = new UserDao();
        dao.setDataSource(dataSource);

        /*
         * 테스트 할 때마다 새로운 오브젝트 생성
        */
        System.out.println(this);

        user1 = new User("gyumee", "박성철", "springno1");
        user2 = new User("leegw700", "이길원", "springno2");
        user3 = new User("bumjin", "박범진", "springno3");
    }

    @Test // JUnit에게 테스트용 메소드임을 알려준다.
    public void addAndGet() throws SQLException { // JUnit 메소드는 반드시 public으로 선언되어야 한다.

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId()); // 첫 번째 User의 id로 get()을 실행하면 첫 번째 User의 값을 가진 오브젝트를 돌려주는지 확인한다.
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId()); // 두 번째 User에 대해서도 같은 방법으로 검증한다.
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));
    }
    
    @Test
    public void count() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    @Test(expected = EmptyResultDataAccessException.class) // 테스트 중에 발생할 것으로 기대하는 예외 클래스를 지정해준다.
    public void getUserFailure() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown_id"); // 이 메소드 실행 중에 예외가 발생해야 한다. 예외가 발생하지 않으면 테스트가 실패한다.
    }
}
