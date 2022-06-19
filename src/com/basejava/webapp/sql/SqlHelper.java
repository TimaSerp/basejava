package com.basejava.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public class SqlHelper {

    @FunctionalInterface
    public interface SqlCommander {
        public void setCommand(PreparedStatement ps) throws SQLException;
    }

    public static void doCommandWithExceptionVoid(ConnectionFactory connectionFactory, String sqlCommand, SqlCommander sqlCommander) {
        try(Connection conn = connectionFactory.getConnection()){
            sqlCommander.setCommand(conn.prepareStatement(sqlCommand));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface SqlCommanderReturnObject<T> {
        public T setCommandReturnObject(PreparedStatement ps) throws SQLException;
    }

    public static <T> T doCommandWithExceptionReturnObject(ConnectionFactory connectionFactory, String sqlCommand, SqlCommanderReturnObject sqlCommander) {
        try(Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlCommand)){
            return (T) sqlCommander.setCommandReturnObject(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
