package springbook.user.dao;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserDaoTest {

    @Autowired UserDao dao; // UserDaoJdbc로 변경해야 하나?
    @Autowired DataSource dataSource;

    User user1;
    User user2;
    User user3;

    public static void main(String[] args) {
        JUnitCore.main("springbook.user.dao.UserDaoTest");
    }

    @Before
    public void setUp() {

        /*
         * 테스트 할 때마다 새로운 오브젝트 생성
        */
        System.out.println(this);

        user1 = new User("gyumee", "박성철", "springno1");
        user2 = new User("leegw700", "이길원", "springno2");
        user3 = new User("bumjin", "박범진", "springno3");
    }

    @Test // JUnit에게 테스트용 메소드임을 알려준다.
    public void addAndGet() { // JUnit 메소드는 반드시 public으로 선언되어야 한다.

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
    public void count() {

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
    public void getUserFailure() {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown_id"); // 이 메소드 실행 중에 예외가 발생해야 한다. 예외가 발생하지 않으면 테스트가 실패한다.
    }

    @Test
    public void getAll() {
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0)); // 데이터가 없을 때는 크기가 0인 리스트 오브젝트가 리턴돼야 한다.

        dao.add(user1); // Id: gyumee
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2); // Id: leegw700
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3); // Id: bumjin
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user3, users3.get(0)); // user3의 id값이 알파벳순으로 가장 빠르므로 getAll()의 첫 번째 엘리먼트여야 한다.
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    private void checkSameUser(User user1, User user2) { // User 오브젝트의 내용을 비교하는 검증 코드. 테스트에서 반복적으로 사용되므로 분리해놓았다.
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateKey() {
        dao.deleteAll();

        dao.add(user1);
        dao.add(user1); // 강제로 같은 사용자를 두 번 등록한다. 여기서 예외가 발생해야 한다.
    }

    @Test
    public void sqlExceptionTranslate() {
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set = // 코드를 이용한 SQLException의 전환
                new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assertThat(set.translate(null, null, sqlEx), // 에러 메시지 만들 때 사용하는 정보이므로 null로 넣어도 된다.
                is(DuplicateKeyException.class));
        }
    }
}
