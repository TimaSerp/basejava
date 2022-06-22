package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;

import java.sql.*;

public class SqlHelper {
    public static ConnectionFactory connectionFactory;

    public static void setConnectionFactory(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @FunctionalInterface
    public interface SqlCommander {
        void setCommand(PreparedStatement ps) throws SQLException;
    }

    public static void doCommandWithExceptionVoid(String sqlCommand, SqlCommander sqlCommander) {
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

    public static <T> T doCommandWithExceptionReturnObject(String sqlCommand, SqlCommanderReturnObject<T> sqlCommander) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            return sqlCommander.setCommandReturnObject(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public static void checkNotExist(PreparedStatement ps, String uuid) throws SQLException {
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}
