package springbook.learningtest.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        this.simpleJdbcTemplate.update("insert into member(id, name, point) VALUES (:id, :name, :point)", new MapSqlParameterSource().addValue("id", "2").addValue("name", "Spring2").addValue("point", 3));
    }

    public Integer findAllCount() {
        return this.simpleJdbcTemplate.queryForInt("select count(*) from member");
    }

    public Integer findCountThanPoint(int min) {
        return this.simpleJdbcTemplate.queryForInt("select count(*) from member where point > :min", new MapSqlParameterSource("min", min));
    }

    public String findNameById(String id) {
        try{
            return this.simpleJdbcTemplate.queryForObject("select name from member where id = ?", String.class, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Member findById(String id) {
        return this.simpleJdbcTemplate.queryForObject("select * from member where id = ?", new BeanPropertyRowMapper<Member>(Member.class), id);
    }

    public List<Member> findAllGreateThanPoint(double point) {
        return this.simpleJdbcTemplate.query("select * from member where point > ?", new BeanPropertyRowMapper<Member>(Member.class), point);
    }

    public Map<String, Object> findByName(String name) {
        return this.simpleJdbcTemplate.queryForMap("select * from member where name = ?", name);
    }

    public List<Map<String, Object>> searchByName(String name) {
        return this.simpleJdbcTemplate.queryForList("select * From member where name like '%"+name+"%'");
    }

    public void updateAllPoint(String[] names, double[] points) {
        this.simpleJdbcTemplate.batchUpdate("update member set point = :point where name = :name", new SqlParameterSource[]{
                new MapSqlParameterSource().addValue("name", names[0]).addValue("point", points[0]),
                new BeanPropertySqlParameterSource(new Member("2", names[1], points[1]))
        });
    }

    public void updataAllNameById(String[] ids, String[] names) {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] {names[0], ids[0]});
        list.add(new Object[] {names[1], ids[1]});
        System.out.println(names[1] + "/" + ids[1]);
        this.simpleJdbcTemplate.batchUpdate("update member set name = ? where id = ?", list);
    }
}
