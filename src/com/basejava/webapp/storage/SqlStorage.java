package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.basejava.webapp.sql.SqlHelper.*;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        setConnectionFactory(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("clear");
        doCommandWithExceptionVoid("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        LOG.info("update " + uuid);
        checkNotExist(uuid);
        doCommandWithExceptionVoid("UPDATE resume r SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, uuid);
            ps.execute();
        });
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        LOG.info("save " + uuid);
        try {
            doCommandWithExceptionVoid("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
                ps.setString(1, uuid);
                ps.setString(2, r.getFullName());
                ps.execute();
            });
        } catch (StorageException e) {
            if (get(r.getUuid()).equals(r)) {
                throw new ExistStorageException(uuid);
            }
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        return doCommandWithExceptionReturnObject("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
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
        checkNotExist(uuid);
        doCommandWithExceptionVoid("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("get all sorted");
        return doCommandWithExceptionReturnObject("SELECT * FROM resume ORDER BY full_name, uuid ASC", ps -> {
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
        return doCommandWithExceptionReturnObject("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
