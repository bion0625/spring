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

    public void executeSql(final String query, String... param) throws SQLException {
        workWithStatementStrategy(
            // 변하지 않는 콜백 클래스 정의와 오브젝트 생성
            new StatementStrategy() { // DI 받은 JdbcContext의 컨텍스트 메소드를 사용하도록 변경한다.
                @Override
                public PreparedStatement makPreparedStatement(Connection c) throws SQLException {
                    PreparedStatement ps = c.prepareStatement(query);
                    if (param.length > 0) {
                        for (int i = 1; i <= param.length; i++) {
                            ps.setString(i, param[i-1]);
                        }
                    }
                    return ps;
                }
            }
        );
    }

}
