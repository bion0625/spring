package springbook.issuetracker.sqlservice.updatable;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import springbook.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;
import springbook.user.sqlservice.SqlNotFountException;

import javax.sql.DataSource;
import java.util.Map;

public class EmbeddedDbSqlRegistry implements UpdatableSqlRegistry {
    SimpleJdbcTemplate jdbc;

    // DataSource를 DI 받아서 SimpleJdbcTemplate 형태로 저장해두고 사용한다.
    public void setDataSource(DataSource dataSource) {
        jdbc = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public void registerSql(String key, String sql) {
        jdbc.update("insert into sqlmap(key_, sql_) values(?,?)", key, sql);
    }

    @Override
    public String findSql(String key) throws SqlNotFountException {
        try {
            return jdbc.queryForObject("select sql_ from sqlmap where key_ = ?", String.class, key);
        }catch (EmptyResultDataAccessException e) {// queryForObject()는 쿼리의 결과가 없으면 이 예외를 발생시킨다.
            throw new SqlNotFountException(String.format("%s에 해당하는 SQL을 찾을 수 없습니다.", key));
        }
    }

    @Override
    public void updateSql(String key, String sql) throws SqlUpdateFailureException {
        /*
         * update()는 SQL 실행 결과로 영향을 받은 레코드의 개수를 리턴한다.
         * 주어진 키(key)를 가진 SQL이 존재하는지를 간단히 확인할 수 있다.
         * */
        int affected = jdbc.update("update sqlmap set sql_ = ? where key_ = ?", sql, key);
        if (affected == 0) {
            throw new SqlUpdateFailureException(String.format("%s에 해당하는 SQL을 찾을 수 없습니다.", key));
        }
    }

    @Override
    public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException {
        for (Map.Entry<String, String> entry : sqlmap.entrySet()) {
            updateSql(entry.getKey(), entry.getValue());
        }
    }
}
