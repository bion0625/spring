package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
    @Bean // 오브젝트 생성을 담당하는 IoC용 메소드라는 표시
    public UserDao userDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker);
        return userDao;
    }

    @Bean
    public UserDao specialUserDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker);
        return userDao;
    }

    @Bean
    public AccountDao accountDao() {
        ConnectionMaker connectionMaker =connectionMaker();
        AccountDao accountDao = new AccountDao(connectionMaker);
        return accountDao;
    }

    @Bean
    public MassageDao massageDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        MassageDao massageDao = new MassageDao(connectionMaker);
        return massageDao;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker(); // 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
    }
}
/*
 * spring context jar 확인
 * https://repo1.maven.org/maven2/org/springframework/spring-context/5.2.9.RELEASE/
*/
