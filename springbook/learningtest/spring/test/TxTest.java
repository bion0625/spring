package springbook.learningtest.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import springbook.learningtest.spring.jpa.Member;
import springbook.learningtest.spring.jpa.MemberTemplateDao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/*
 * VM 옵션 (InstrumentationLoadTimeWeaver 관련)
 * -javaagent:springbook/lib/spring-instrument-3.0.3.RELEASE.jar
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/springbook/learningtest/spring/jpa/JpaTest-context.xml")
public class TxTest {

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    MemberTemplateDao dao;

    @Test
    public void txTest() {
        new TransactionTemplate(this.transactionManager).execute(
                new TransactionCallback<Object>() {
                    @Override
                    public Object doInTransaction(TransactionStatus status) {
                        // 예외가 발생하지 않아도 트랜젝션이 끝날 때 트랜젝션이 롤백되도록 설정한다.
                        status.setRollbackOnly();

                        // execute() 메소드에 의해 시작된 트랜젝션 안에서 모든 DB 작업이 진행된다.
                        dao.deleteAll();
                        dao.add(new Member(10, "Spring", 7.8));
                        assertThat(dao.count(), is(1L));
                        return null;
                    }
                }
        );
    }

    @Test
    @Transactional
//    @Rollback(false)
    public void txTestWithTransactionalAnnotation() {
        // 강제 롤백이 적용된 트랜젝션 안에서 실행된다.
        dao.deleteAll();
        dao.add(new Member(10, "Spring", 7.8));
        assertThat(dao.count(), is(1L));
    }

    @Test
    @Transactional
    public void ormTest() {
        dao.deleteAll();
        Member member = new Member(10, "Spring", 7.8);
        dao.add(member);
        dao.flush();
        dao.clear();

        assertThat(member, is(dao.get(member.getId())));
    }
}
