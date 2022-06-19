package com.basejava.webapp.sql;

import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    @FunctionalInterface
    public interface SqlCommander {
        void setCommand(PreparedStatement ps) throws SQLException;
    }

    public static void doCommandWithExceptionVoid(ConnectionFactory connectionFactory, String sqlCommand, SqlCommander sqlCommander) {
        try (Connection conn = connectionFactory.getConnection()) {
            sqlCommander.setCommand(conn.prepareStatement(sqlCommand));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface SqlCommanderReturnObject<T> {
        T setCommandReturnObject(PreparedStatement ps) throws SQLException;
    }

    public static <T> T doCommandWithExceptionReturnObject(ConnectionFactory connectionFactory, String sqlCommand, SqlCommanderReturnObject<T> sqlCommander) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            return sqlCommander.setCommandReturnObject(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
