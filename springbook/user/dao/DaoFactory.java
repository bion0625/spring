package springbook.user.dao;

public class DaoFactory {
    public UserDao userDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }

    public AccountDao accountDao() {
        ConnectionMaker connectionMaker =connectionMaker();
        AccountDao accountDao = new AccountDao(connectionMaker);
        return accountDao;
    }

    public MassageDao massageDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        MassageDao massageDao = new MassageDao(connectionMaker);
        return massageDao;
    }

    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker(); // 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
    }
}
/*
 * spring context jar 확인
 * https://repo1.maven.org/maven2/org/springframework/spring-context/5.2.9.RELEASE/
*/
