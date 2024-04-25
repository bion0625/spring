package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserDaoJdbc implements UserDao{

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper =  // ResultSet 한 로우의 결과를 오브젝트에 매핑해주는 RowMapper 콜백
        new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setLevel(Level.valueOf(rs.getInt("level")));
                user.setLogin(rs.getInt("login"));
                user.setRecommend(rs.getInt("recommend"));
                return user;
            }
        };

    public void setDataSource(DataSource dataSource) { // 수정자 메소드이면서 JdbcContext에 대한 생성, DI 작업을 동시에 수행한다.
        this.jdbcTemplate = new JdbcTemplate(dataSource); // DataSource 오브젝트는 JdbcTemplate을 만든 후에는 사용하지 않으니 저장해두지 않아도 된다.
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public void add(final User user) { // 애플리케이션 레벨의 체크 예외
        this.jdbcTemplate.update(
            "insert into users(id, name, password, email, level, login, recommend) " + 
            "values(?,?,?,?,?,?,?) ", user.getId(), user.getName(), 
            user.getPassword(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend()
        );
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
            "select * from users where id = ?", 
            new Object[] {id}, // SQL에 바인딩할 파라미터 값. 가변인자 대신 배열을 사용한다.
            this.userMapper);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
            "select * from users order by id", 
            this.userMapper);
    }

    public int getCount() {
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
            "update users set name = ?, password = ?, email = ?, level = ?, login = ? , " + 
            "recommend = ? where id = ?", user.getName(), user.getPassword(),
            user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
            user.getId()
        );
    }
}
