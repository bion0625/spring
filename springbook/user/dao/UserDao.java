package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;

public class UserDao {

    private JdbcContext context;

    public void setJdbcContext(JdbcContext context) { // JdbcContext를 DI받도록 한다.
        this.context = context;
    }

    private DataSource dataSource; // 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알 필요가 없다.

    private ConnectionMaker connectionMaker; // 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알 필요가 없다.

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void deleteAll() throws SQLException {
        context.workWithStatementStrategy(new StatementStrategy() { // DI 받은 JdbcContext의 컨텍스트 메소드를 사용하도록 변경한다.
            @Override
            public PreparedStatement makPreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("delete from users");
                return ps;
            }
        });
    }

    public void add(final User user) throws SQLException {
        context.workWithStatementStrategy(new StatementStrategy() { // 익명 내부 클래스는 구현하는 인터페이스를 생성자처럼 이용해서 오브젝트로 만든다.
            @Override
            public PreparedStatement makPreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });
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
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");

            // resultSet도 다양한 SQLException이 발생할 수 있는 코드이므로 try 블록 안에 둬야 한다.
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close(); // 만들어진 resultSet을 닫아주는 기능. close()는 만들어진 순서의 반대로 하는 것이 원칙이다.
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
