package springbook.user.service;

import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService implements UserLevelUpgradePolicy {
    UserDao userDao;

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if(canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
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
            default:throw new IllegalArgumentException("Unknown Level: " + currentLevel);
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
