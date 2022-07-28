package com.basejava.webapp.sql;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.util.ExceptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.basejava.webapp.util.ExceptionUtil.convertException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sqlCommand) {
        execute(sqlCommand, PreparedStatement::execute);
    }

    @FunctionalInterface
    public interface SqlExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public <T> T execute(String sqlCommand, SqlExecutor<T> sqlExecutor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            return sqlExecutor.execute(ps);
        } catch (SQLException e) {
            throw convertException(e);
        }
    }

    @FunctionalInterface
    public interface SqlTransaction<T> {
        void execute(Connection conn) throws SQLException;
    }

    public <T> void transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                executor.execute(conn);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
