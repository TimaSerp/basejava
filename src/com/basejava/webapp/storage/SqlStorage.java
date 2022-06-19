package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ConnectionFactory;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;

import static com.basejava.webapp.sql.SqlHelper.doCommandWithExceptionReturnObject;
import static com.basejava.webapp.sql.SqlHelper.doCommandWithExceptionVoid;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("clear");
        doCommandWithExceptionVoid(connectionFactory, "DELETE FROM resume", ps -> ps.execute());
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        LOG.info("update " + uuid);
//        checkExist(uuid);
        doCommandWithExceptionVoid(connectionFactory, "UPDATE resume r SET uuid=?, full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            ps.setString(2, r.getFullName());
            ps.setString(3, uuid);
            ps.execute();
        });
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        LOG.info("save " + uuid);
        doCommandWithExceptionVoid(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, uuid);
            ps.setString(2, r.getFullName());
            if (ps.executeQuery().next()) {
                throw new ExistStorageException(uuid);
            }
            ps.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        Resume resume = doCommandWithExceptionReturnObject(connectionFactory, "SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            return r;
        });
        return resume;
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
//        checkExist(uuid);
        doCommandWithExceptionVoid(connectionFactory, "DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("get all sorted");
        doCommandWithExceptionReturnObject(connectionFactory, "SELECT * FROM resume ORDER BY full_name, uuid ASC", ps -> ps.execute());
        return null;
    }

    @Override
    public int size() {
        LOG.info("size");
        return doCommandWithExceptionReturnObject(connectionFactory, "SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("1");
            }
            return rs.getInt(1);
        });
    }

//    private void checkExist(String uuid) {
//        doCommandWithExceptionVoid(connectionFactory, "SELECT * FROM resume WHERE uuid =?", ps -> {
//            ps.setString(1, uuid);
//            if (ps.execute() == false) {
//                throw new NotExistStorageException(uuid);
//            }
//        });
//    }
}
