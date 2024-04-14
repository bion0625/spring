// package springbook.user.dao;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class CountingDaoFactory {

//     @Bean
//     public UserDao userDao() {
//         UserDao userDao = new UserDao();
//         userDao.setConnectionMaker(connectionMaker());
//         return userDao; // 모든 DAO는 여전히 connectionMaker()에서 만들어진 오브젝트를 DI 받는다.
//     }

//     @Bean
//     public UserDao specialUserDao() {
//         UserDao userDao = new UserDao();
//         userDao.setConnectionMaker(connectionMaker());
//         return userDao;
//     }

//     @Bean
//     public ConnectionMaker connectionMaker() {
//         return new CountingConnectionMaker(realConnectionMaker());
//     }

//     @Bean
//     public ConnectionMaker realConnectionMaker() {
//         return new DConnectionMaker();
//     }
// }
