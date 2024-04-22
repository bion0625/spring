package springbook.user.service;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService implements UserLevelUpgradePolicy {
    UserDao userDao;

    private DataSource dataSource;

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    public void setDataSource(DataSource dataSource) { // Connection을 생성할 때 사용할 DataSource를 DI받도록 한다.
        this.dataSource = dataSource;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() throws Exception {
        // 트랜잭션 동기화 관리자를 이용해 동기화 작업을 초기화한다.
        TransactionSynchronizationManager.initSynchronization();

        // DB 커넥션을 생성하고 트랜젝션을 시작한다. 이후의 DAO 작업은 모두 여기서 시작한 트랜젝션 안에서 진행된다.
        // DB 커넥션 생성과 동기화를 함께 해주는 유틸리티 메소드
        Connection c = DataSourceUtils.getConnection(dataSource);
        c.setAutoCommit(false);

        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if(canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            c.commit(); // 정상적으로 작업을 마치면 트랜젝션 커밋
        } catch (Exception e) { // 예외가 발생하면 롤백한다.
            c.rollback();
            throw e;
        } finally {

            // 스프링 유틸리티 메소드를 이용해 DB 커넥션을 안전하게 닫는다.
            DataSourceUtils.releaseConnection(c, dataSource);

            // 동기화 작업 종료 및 정리
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
        
    }

    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        // 레벨 별로 구분해서 로직을 판단한다.
        switch (currentLevel) {
            case BASIC:return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER:return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
            case GOLD:return false;
            // 현재 로직에서 다룰 수 없는 레벨이 주어지면 예외를 발생시킨다.
            // 새로운 레벨이 추가되고 로직을 수정하지 않으면 에러가 나서 확인할 수 있다.
            // java.lang.IllegalStateException: Failed to load ApplicationContext 오류 때문에 변경: 원인 추후 확인
            // default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
            default: return false;
        }
    }

    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
