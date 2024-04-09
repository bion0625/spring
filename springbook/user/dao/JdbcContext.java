package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) throws SQLException { // DataSource 타입 빈을 DI 받을 수 있게 준비해둔다.
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = stmt.makPreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) { try { ps.close(); } catch(SQLException e) {} }
            if (c != null) { try{ c.close(); } catch(SQLException e) {} }
        }
    }


}
