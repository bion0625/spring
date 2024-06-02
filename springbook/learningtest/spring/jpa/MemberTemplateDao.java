package springbook.learningtest.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public class MemberTemplateDao extends JpaDaoSupport {

    @Autowired
    protected void init(EntityManagerFactory emf) {
        super.setEntityManagerFactory(emf);
    }
}
