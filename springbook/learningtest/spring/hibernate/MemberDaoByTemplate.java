package springbook.learningtest.spring.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class MemberDaoByTemplate extends HibernateDaoSupport {

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    public void addMember(Member member) {
        getHibernateTemplate().save(member);
    }

    public Member findMemberById(int id) {
        return getHibernateTemplate().get(Member.class, id);
    }

    public void deleteMember(Member member) {
        getHibernateTemplate().delete(member);
    }

    public List<Member> findMemberAll() {
        return getHibernateTemplate().loadAll(Member.class);
    }

    public long getAllCount() {
        return getHibernateTemplate().execute(
                new HibernateCallback<Long>() {
                    @Override
                    public Long doInHibernate(Session s) throws HibernateException, SQLException {
                        // 콜백 오브젝트의 메소드에서는 하이버네이트의 Session을 직접 사용할 수 있다.
                        return (Long) s.createQuery("select count(m) from Member m").uniqueResult();
                    }
                }
        );
    }
}
