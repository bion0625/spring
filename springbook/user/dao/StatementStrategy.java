package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
    PreparedStatement makPreparedStatement(Connection c) throws SQLException;
}
