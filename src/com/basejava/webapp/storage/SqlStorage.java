package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ConnectionFactory;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.basejava.webapp.sql.SqlHelper.doCommandWithExceptionReturnObject;
import static com.basejava.webapp.sql.SqlHelper.doCommandWithExceptionVoid;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("clear");
        doCommandWithExceptionVoid(connectionFactory, "DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        LOG.info("update " + uuid);
        get(uuid);
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
        try {
            get(uuid);
            throw new ExistStorageException(uuid);
        } catch (NotExistStorageException ignored) {
            doCommandWithExceptionVoid(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
                ps.setString(1, uuid);
                ps.setString(2, r.getFullName());
                ps.execute();
            });
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        return doCommandWithExceptionReturnObject(connectionFactory, "SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        get(uuid);
        doCommandWithExceptionVoid(connectionFactory, "DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("get all sorted");
        return doCommandWithExceptionReturnObject(connectionFactory, "SELECT * FROM resume ORDER BY full_name, uuid ASC", ps -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(get(rs.getString("uuid")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        LOG.info("size");
        return getAllSorted().size();
    }
}
