package springbook.user.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    List<User> users; // 테스트 픽스처

    @Before
    public void setUp() {
        users = Arrays.asList( // 배열을 리스트로 만들어주는 편리한 메소드. 배열을 가변인자로 넣어주면 더욱 편리하다.
            new User("bumjin", "박범진", "p1", Level.BASIC, 49, 0),
            new User("joytouch", "강명성", "p2", Level.BASIC, 50, 0),
            new User("erwins", "신승한", "p3", Level.SILVER, 60, 29),
            new User("madnite1", "이상호", "p4", Level.SILVER, 60, 30),
            new User("green", "오민규", "p5", Level.GOLD, 100, 100)
        );
    }

    // @Test
    // public void bean() {
    //     assertThat(this.userService, is(notNullValue()));
    // }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        userService.upgradeLevels();

        // 각 사용자별로 업그레이드 후의 예상 레벨을 검증한다.
        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    // DB에서 사용자 정보를 가져와 레벨을 확인하는 코드가 중복되므로 헬퍼 메소드로 분기했다.
    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4); // GOLD 레벨 -> GOLD 레벨이 이미 지정된 User라면 레벨을 초기화하지 않아야 한다.
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null); // 레벨이 비어 있는 사용자. 로직에 따라 증록 중에 BASIC 레벨도 설정돼야 한다.

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        // DB에 저장된 결과를 가져와야 한다.
        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
    }
}
