package springbook.user.service;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import static springbook.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserService.MIN_RECCOMEND_FOR_GOLD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {

    @Autowired
    UserDao userDao;

    @Autowired
    UserLevelUpgradePolicy userService;

    @Autowired
    PlatformTransactionManager transactionManager;

    List<User> users; // 테스트 픽스처

    @Before
    public void setUp() {
        users = Arrays.asList( // 배열을 리스트로 만들어주는 편리한 메소드. 배열을 가변인자로 넣어주면 더욱 편리하다.
            // 테스트에서는 경계값을 사용하는 것이 좋다.
            new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
            new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
            new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
            new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
            new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    // @Test
    // public void bean() {
    //     assertThat(this.userService, is(notNullValue()));
    // }

    @Test
    public void upgradeLevels() throws Exception {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) { // 어떤 레벨로 바뀔 것인가가 아니라, 다음 레벨로 업그레이드될 것인가 아닌가를 지정한다.
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            // 업그레이드가 일어났는지 확인.
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel())); // 다음 레벨이 무엇인지는 Level에게 물어보면 된다.
        }
        else {
            // 업그레이드가 일어나지 않았는지 확인.
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }

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

    static class TestUserService extends UserService {
        private String id;

        public TestUserService(String id) { // 예외를 발생시킬 User 오브젝트의 id를 지정할 수 있게 만든다.
            this.id = id;
        }

        public void upgradeLevel(User user) { // UserService의 메소드를 오버라이드한다.
            // 지정된 id의 user 오브젝트가 발견되면 예외를 던져서 작업을 강제로 중단한다.
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {}

    @Test
    public void upgradeAllOrNothing() throws Exception {
        // 예외를 발생시킬 네 번째 사용자의 id를 넣어서 테스트용 UserService 대역 오브젝트를 생성한다.
        UserService testUserService = new TestUserService(users.get(3).getId());
        // userService 빈의 프로퍼티 설정과 동일한 수동 DI
        testUserService.setUserDao(this.userDao);
        testUserService.setTransactionManager(transactionManager);

        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        try {
            // testUserService는 업그레이드 작업 중에 예외가 발생해야 한다. 정상 종료라면 문제가 있으니 실패
            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) { // testUserService가 던져주는 예외를 잡아서 계속 진행되도록 한다. 그 외의 예외라면 테스트 실패
        }

        // 예외가 발생하기 전에 레벨 변경이 있었던 사용자의 레벨이 처음 상태로 바뀌었나 확인
        checkLevelUpgraded(users.get(1), false);
    }
}
