package springbook.learningtest.spring.tx;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import springbook.learningtest.spring.jdbc.Member;

import javax.sql.DataSource;
import java.util.List;

public class MemberDaoByClassProxy {
    SimpleJdbcInsert jdbcMemberInsert;
    SimpleJdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcMemberInsert = new SimpleJdbcInsert(dataSource).withTableName("member");
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
    public void add(Member m) {
        this.jdbcMemberInsert.execute(new BeanPropertySqlParameterSource(m));
    }

    public void add(List<Member> members) {
        for (Member member : members) {
            add(member);
        }
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from member");
    }

    public long count() {
        return this.jdbcTemplate.queryForInt("select count(*) from member");
    }
}
