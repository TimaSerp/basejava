package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("clear");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        LOG.info("update " + uuid);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name=? WHERE r.uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contacts WHERE resume_uuid=?")){
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertContacts(r, conn);
        });
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        LOG.info("save " + uuid);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, uuid);
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(r, conn);
        });
    }

    private void insertContacts(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contacts (type, value, resume_uuid) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, e.getKey().name());
                ps.setString(2, e.getValue());
                ps.setString(3, r.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        return sqlHelper.execute("" +
                "SELECT * FROM resume r " +
                "LEFT JOIN contacts c " +
                "ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            executeContacts(rs, r);
            return r;
        });
    }

    private void executeContacts(ResultSet rs, Resume r) throws SQLException {
        do {
            String value = rs.getString("value");
            if (value != null) {
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type, value);
            }
        } while (rs.next());
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume r WHERE r.uuid=?")) {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("get all sorted");
        return sqlHelper.execute("SELECT * FROM resume LEFT JOIN contacts c ON resume.uuid=c.resume_uuid ORDER BY full_name, uuid", ps -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
//            Так как все резюме отсортированы, то сначала будет обрабатываться одно резюме, только после полной его обработки следующее.
//            Из-за этого мы можем сохранять резюме, с которым мы работаем, и в него добавлять контакты, когда резюме меняется,
//            currentResume тоже меняется.
            Resume currentResume = new Resume("new");
            while (rs.next()) {
                Resume newResume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                String type = rs.getString("type");
                String value = rs.getString("value");
                if (currentResume.getUuid().equals(newResume.getUuid()) && value != null) {
                    currentResume.addContact(ContactType.valueOf(type), value);
                } else {
                    if (value != null) {
                        newResume.addContact(ContactType.valueOf(type), value);
                    }
                    resumes.add(newResume);
                    currentResume = newResume;
                }
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        LOG.info("size");
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
