package springbook.user.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
    @Bean // 오브젝트 생성을 담당하는 IoC용 메소드라는 표시
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }
    // XML
    // <bean id="userDao" class="springbook.user.dao.userDao">
    //  <property name="connectionMaker" ref="connectionMaker"/>
    // <bean id="userDao" class="springbook.user.dao.userDao">

    @Bean
    public UserDao specialUserDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public AccountDao accountDao() {
        AccountDao accountDao = new AccountDao(connectionMaker());
        return accountDao;
    }

    @Bean
    public MassageDao massageDao() {
        MassageDao massageDao = new MassageDao(connectionMaker());
        return massageDao;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker(); // 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
    }
    // XML
    // <bean id="connectionMaker" class="springbook.user.dao.DConnectionMaker"/>

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/mysql");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");

        return dataSource;
    }
}
/*
 * spring context jar 확인
 * https://repo1.maven.org/maven2/org/springframework/spring-context/5.2.9.RELEASE/
*/

/*
 * XML
 *  <beans>
 *      <bean id="myConnectionMaker" class="springbook.user.dao.DConnectionMaker" />
 *      <bean id="userDao" class="springbook.user.dao.userDao">
 *          <property name="connectionMaker" ref="myConnectionMaker"/>
 *      </bean>
 *  </beans>
 * 
*/