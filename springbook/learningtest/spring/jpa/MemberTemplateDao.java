package springbook.learningtest.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

@Repository
public class MemberTemplateDao extends JpaDaoSupport {

    @Autowired
    protected void init(EntityManagerFactory emf) {
        super.setEntityManagerFactory(emf);
    }

    public void deleteAll() {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager entityManager) throws PersistenceException {
                return entityManager.createQuery("delete from Member m").executeUpdate();
            }
        });
    }

    public void add(Member member) {
        getJpaTemplate().persist(member);
    }

    public long count() {
        Object count = getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager entityManager) throws PersistenceException {
                return entityManager.createQuery("select count(m) from Member m").getSingleResult();
            }
        });
        return (Long) count;
    }

    public Member get(int id) {
        return getJpaTemplate().find(Member.class, id);
    }

    public void flush() {
        getJpaTemplate().flush();
    }

    public void clear() {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager entityManager) throws PersistenceException {
                entityManager.clear();
                return null;
            }
        });
    }
}
