package springbook.learningtest.spring.jpa;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class MemberDao {

    @PersistenceContext
    EntityManager em;

    public void addMember(Member member) {
        em.persist(member);
    }
}
