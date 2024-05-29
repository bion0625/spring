package springbook.learningtest.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class MemberDao {
    SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void init(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public void deleteAll() {
        this.simpleJdbcTemplate.update("delete from member");
    }

    public void testSaveOne() {
//        this.simpleJdbcTemplate.update(
//                "insert into member(id, name, point) VALUES (?, ?, ?)",
//                "1", "Spring", 3.5);

//        Map<String, String> map = new HashMap<>();
//        map.put("id", "1");
//        map.put("name", "Spring");
//        map.put("point", "3.5");
//        this.simpleJdbcTemplate.update(
//                "insert into member(id, name, point) VALUES (:id, :name, :point)", map);

//        Member member = new Member("1", "Spring", 3.5);
//        this.simpleJdbcTemplate.update(
//                "insert into member(id, name, point) VALUES (:id, :name, :point)", new BeanPropertySqlParameterSource(member));

        this.simpleJdbcTemplate.update("insert into member(id, name, point) VALUES (:id, :name, :point)", new MapSqlParameterSource().addValue("id", "1").addValue("name", "Spring").addValue("point", 3.5));
    }

    public Integer findAllCount() {
        return this.simpleJdbcTemplate.queryForInt("select count(*) from member");
    }
}
