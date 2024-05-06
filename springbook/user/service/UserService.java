package springbook.user.service;

import java.util.List;

import springbook.user.domain.User;

public interface UserService {
    // DAO 메소드와 1:1 대응되는 CRUD 메소드이지만 add() 처럼 단순히 위임 이상의 로직을 가질 수 있다.
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    void update(User user);

    void upgradeLevels();
}
