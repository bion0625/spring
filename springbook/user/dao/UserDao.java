package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import springbook.user.domain.User;

public class UserDao {

    private JdbcContext jdbcContext;

    private DataSource dataSource; // 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알 필요가 없다.

    private ConnectionMaker connectionMaker; // 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알 필요가 없다.

    private JdbcTemplate jdbcTemplate;

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void setDataSource(DataSource dataSource) throws SQLException { // 수정자 메소드이면서 JdbcContext에 대한 생성, DI 작업을 동시에 수행한다.
        this.jdbcContext = new JdbcContext(); // JdbcContext 생성(ioc)
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcContext.setDataSource(dataSource); // 의존 오브젝트 주입(DI)
        this.dataSource = dataSource; // 아직 JdbcContext를 적용하지 않은 메소드를 위해 저장해둔다.
    }

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from users");
    }

    public void add(final User user) throws SQLException {
        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection(); // 인터페이스에 정의된 메소드를 사용하므로 클래스가 바뀐다고 해도 메소드 이름이 변경될 걱정은 없다.

        PreparedStatement ps = c.prepareStatement(
            "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;
        if(rs.next()){ // id를 조건으로 한 쿼리의 결과가 있으면 User 오브젝트를 만들고 값을 넣어준다.
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) throw new EmptyResultDataAccessException(1); // 결과가 없으면 User는 null 상태 그대로일 것이다. 이를 확인해서 예외를 던져준다.

        return user;
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }
}
