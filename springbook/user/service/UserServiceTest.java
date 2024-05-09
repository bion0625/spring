package springbook.user.service;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import static springbook.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
@Transactional
/*
 * 롤백 여부에 대한 기본 설정과 트랜잭션 매니저 빈을 지정하는데 사용할 수 있다.
 * 디폴트 트랜잭션 매니저 아이디는 관례를 따라서 transactionManager로 되어 있다.
*/
@TransactionConfiguration(defaultRollback = false)
public class UserServiceTest {
    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    UserDao userDao;

    @Autowired UserService userService;
    /*
     * 같은 타입의 빈이 두 개 존재하기 때문에 필드 이름을 기준으로 주입될 빈이 결정된다.
     * 자동 프록시 생성기에 의해 트랜젝션 부가기능이 testUserService 빈에 적용됐는지 확인하는 것이 목적이다.
    */
    @Autowired UserService testUserService;

    @Autowired
    MailSender mailSender;

    @Autowired ApplicationContext context; // 팩토리 빈을 가져오려면 애플리케이션 컨텍스트가 필요하다.

    List<User> users; // 테스트 픽스처

    @Before
    public void setUp() {
        users = Arrays.asList( // 배열을 리스트로 만들어주는 편리한 메소드. 배열을 가변인자로 넣어주면 더욱 편리하다.
            // 테스트에서는 경계값을 사용하는 것이 좋다.
            new User("bumjin", "박범진", "p1", "test01@test.com", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
            new User("joytouch", "강명성", "p2", "test02@test.com", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
            new User("erwins", "신승한", "p3", "test03@test.com", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
            new User("madnite1", "이상호", "p4", "test04@test.com", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
            new User("green", "오민규", "p5", "test05@test.com", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    // @Test
    // public void bean() {
    //     assertThat(this.userService, is(notNullValue()));
    // }

    static class MockUserDao implements UserDao {
        private List<User> users = new ArrayList<>(); // 레벨 업그레이드 후보 User 오브젝트 목록
        private List<User> updated = new ArrayList<>(); // 업그레이드 대상 오브젝트를 저장해둘 목록

        public MockUserDao(List<User> users) {
            this.users = users;
        }

        public List<User> getUpdated() {
            return this.updated;
        }

        @Override
        public List<User> getAll() { // 스텁 기능 제공
            return this.users;
        }

        @Override
        public void update(User user) { // 목 오브젝트 기능 제공
            this.updated.add(user);
        }

        // 테스트에 사용되지 않는 메소드
        @Override
        public void deleteAll() {throw new UnsupportedOperationException();};
        @Override
        public void add(User user) {throw new UnsupportedOperationException();};
        @Override
        public User get(String id) {throw new UnsupportedOperationException();};
        @Override
        public int getCount() {throw new UnsupportedOperationException();};
        
    }

    @Test
    @DirtiesContext
    public void upgradeLevels() throws Exception {
        // 고립된 테스트에서는 테스트 대상 오브젝트를 직접 설정하면 된다.
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        // 목 오브젝트로 만든 userDao를 직접 DI 해준다.
        MockUserDao mockUserDao = new MockUserDao(users);
        userServiceImpl.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        // MockUserDao로부터 업데이트 결과를 가져온다.
        List<User> updated = mockUserDao.getUpdated();
        // 업데이트 횟수와 정보를 확인한다.
        assertThat(updated.size(), is(2));
        checkUserAndLevel(updated.get(0), "joytouch", Level.SILVER);
        checkUserAndLevel(updated.get(1), "madnite1", Level.GOLD);

        List<String> reuqest = mockMailSender.getRequests();
        assertThat(reuqest.size(), is(2));
        assertThat(reuqest.get(0), is(users.get(1).getEmail()));
        assertThat(reuqest.get(1), is(users.get(3).getEmail()));
    }

    // id와 level을 확인하는 간단한 메소드
    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId(), is(expectedId));
        assertThat(updated.getLevel(), is(expectedLevel));
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

    // Java9 이후부터는 reflection API 관련 기능에 제한 이슈 오류코드 (mock 관련)
    // Caused by: java.lang.reflect.InaccessibleObjectException: Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module @2a18f23c at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:354) at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:297) at java.base/java.lang.reflect.Method.checkCanSetAccessible(Method.java:199) at java.base/java.lang.reflect.Method.setAccessible(Method.java:193) at net.sf.cglib.core.ReflectUtils$2.run(ReflectUtils.java:56) at java.base/java.security.AccessController.doPrivileged(AccessController.java:318) at net.sf.cglib.core.ReflectUtils.<clinit>(ReflectUtils.java:46)
    // 위 이슈 테스트 관련 아래 확인: vscode 테스트 관련 세팅
    // "java.test.config": {
    //     "vmArgs": ["--add-opens=java.base/java.lang=ALL-UNNAMED"]
    // }
    @Test
    public void mockUpgradeLevels() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        // 다이내믹한 목 오브젝트 생성과 메소드의 리턴 값 설정, 그리고 DI까지 세 줄이면 충분하다.
        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        // 리턴 값이 없는 메소드를 가진 목 오브젝트는 더욱 간단하게 만들 수 있다.
        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        // 목 오브젝트가 제공하는 검증 기능을 통해서 어떤 메소드가 몇 번 호출됐는지, 파라미터는 무엇인지 확인할 수 있다.
        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel(), is(Level.SILVER));
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel(), is(Level.GOLD));

        ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
        // 파라미터를 정미랗게 검사하기 위해 캡쳐할 수도 있다.
        verify(mockMailSender, times(2)).send(mailMessageArg.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
        assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
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

    // 포인트컷의 클래스필터에 선정되도록 이름 변경
    // 이래서 처음부터 이름을 잘 지어야 한다.
    static class TestUserServiceImpl extends UserServiceImpl {
        private String id = "madnite1"; // 테스트 픽스처의 users(3)의 id 값을 고정시켜버렸다.

        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }

        // 읽기 전용 트랜잭션의 대상인 get으로 시작하는 메소드를 오버라이드한다.
        @Override
        public List<User> getAll() {
            for (User user : super.getAll()) {
                super.update(user); // 강제로 쓰기 시도를 한다. 여기서 읽기전용 속성으로 인한 예외가 발생해야 한다.
            }
            // 메소드가 끝나기 전에 예외가 발생해야 하니 리턴 값은 별 의미 없다. 적당한 값을 넣어서 컴파일만 되게 한다.
            return null;
        }
    }

    static class TestUserServiceException extends RuntimeException {}

    /*
     * 스프링 컨텍스트의 빈 설정을 변경하지 않으므로 @DirtiesContext 애노테이션은 제거됐다.
     * 모든 테스트를 위한 DI 작업은 설정파일을 통해 서버에서 진행되므로 테스트 코드 자체는 단순해진다.
    */
    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void upgradeAllOrNothing() throws Exception {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        try {
            this.testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) { // testUserService가 던져주는 예외를 잡아서 계속 진행되도록 한다. 그 외의 예외라면 테스트 실패
        }

        checkLevelUpgraded(users.get(1), false);
    }

    @Test(expected = TransientDataAccessResourceException.class)
    @Transactional(propagation = Propagation.NEVER)
    public void readOnlyTransactionAttribute() {
        testUserService.getAll(); // 트랜잭션 속성이 제대로 적용됐다면 여기서 읽기전용 속성을 위반했기 때문에 예외가 발생해야 한다.
    }

    @Test
    public void advisorAutoProxyCreator() {
        assertThat(testUserService, is(java.lang.reflect.Proxy.class));
    }

    @Test
    @Rollback // 메소드에서 디폴트 설정과 그 밖의 롤백 방법으로 재설정할 수 있다.
    public void transactionSync() {
        userService.deleteAll();
        userService.add(users.get(0));
        userService.add(users.get(1));
    }
}
