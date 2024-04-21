package springbook.user.service;

import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService {
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            Boolean changed = null; // 레벨의 변화가 있는지를 확인하는 플래그
            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) { // BASIC 레벨 업그레이드 작업
                user.setLevel(Level.SILVER);
                changed = true;
            }
            else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) { //SILVER 레벨 업그레이드 작업
                user.setLevel(Level.GOLD);
                changed = true;
            }
            else if (user.getLevel() == Level.GOLD) {changed = false;} // GOLD 레벨은 변경이 일어나지 않는다.
            else { changed = false; } // 일치하는 조건이 없으면 변경 없음
            if (changed) {userDao.update(user);} // 레벨의 변경이 있는 경우에만 update() 호출
        }
    }
}
