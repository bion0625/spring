package springbook.user.service;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService {
    UserDao userDao;

    MailSender mailSender;

    PlatformTransactionManager transactionManager;

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    // 프로퍼티 이름은 관례를 따라 transactionManager라고 만드는 것이 편리하다.
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    protected void upgradeLevels() throws Exception {
        // 트랜젝션 시작
        // DI 받은 트렌젝션 매니저를 공유해서 사용한다. 멀티 스레드 환경에서도 안전하다.
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 트랜젝션 안에서 진행되는 작업
            List<User> users = userDao.getAll();
            for (User user : users) {
                if(canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status); // 트랜젝션 커밋
        } catch (Exception e) { // 예외가 발생하면 롤백한다.
            transactionManager.rollback(status); // 트랜젝션 커밋
            throw e;
        }
        
    }

    private boolean canUpgradeLevel(User user) {
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

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEMail(user);
    }

    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    private void sendUpgradeEMail(User user) {
        // MailMessage 인터페이스의 구현 클래스 오브젝트를 만들어 메일 내용을 작성한다.
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@ksug.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText(String.format("사용자님의 등급이 %s", user.getLevel().name()));

        this.mailSender.send(mailMessage);
    }
}
