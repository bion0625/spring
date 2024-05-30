package springbook.learningtest.spring.jdbc;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterDao extends AbstractSimpleJdbcDaoSupport {

    SimpleJdbcInsert registerInsert;

    @Override
    protected void initJdbcOjects() {
        this.registerInsert = new SimpleJdbcInsert(getDataSource())
                .withTableName("register")
                .usingGeneratedKeyColumns("id");
    }

    public Integer save(String name) {
        return this.registerInsert.executeAndReturnKey(new MapSqlParameterSource("name", name)).intValue();
    }

    public String findNameById(int id) {
        return simpleJdbcTemplate.queryForObject("select name from register where id = ?", String.class, id);
    }
}
