package springbook.learningtest.spring.jdbc;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class AbstractSimpleJdbcDaoSupport extends JdbcDaoSupport {
    protected SimpleJdbcTemplate simpleJdbcTemplate;

    // JdbcDaoSupport에서 제공하는 초기화용 메소드다. dataSource가 준비된 후에 호출된다.
    @Override
    protected void initTemplateConfig() {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(getDataSource());
        initJdbcOjects();
    }

    // SimpleJdbcDaoSupport를 상속받는 DAO에서 SimpleJdbcInsert 등을 생성할 때 오버라이드해서 사용할 수 있는 초기화 메소드다.
    protected void initJdbcOjects() {}
}
