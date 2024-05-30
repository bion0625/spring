package springbook.learningtest.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class RegisterDao {

    SimpleJdbcInsert registerInsert;
    SimpleJdbcTemplate jdbcTemplate;

    @Autowired
    public void init(DataSource dataSource) {
        this.registerInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("register")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public Integer save(String name) {
        return this.registerInsert.executeAndReturnKey(new MapSqlParameterSource("name", name)).intValue();
    }

    public String findNameById(int id) {
        return jdbcTemplate.queryForObject("select name from register where id = ?", String.class, id);
    }
}
