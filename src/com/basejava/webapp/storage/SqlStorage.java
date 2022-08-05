package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import static com.basejava.webapp.model.SectionType.*;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlHelper sqlHelper;
    private static final String CONTACTS = "contacts";
    private static final String SECTIONS = "sections";


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contacts WHERE resume_uuid=?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertContacts(r, conn);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM sections WHERE resume_uuid=?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertSections(r, conn);
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
            insertSections(r, conn);
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        Resume resume = sqlHelper.execute("" +
                "SELECT * FROM resume r " +
                "LEFT JOIN contacts c " +
                "ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
        sqlHelper.execute("SELECT * FROM contacts WHERE resume_uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String value = rs.getString("value");
                if (value != null) {
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    resume.addContact(type, value);
                }
            }
            return null;
        });
        sqlHelper.execute("SELECT * FROM sections WHERE resume_uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String value = rs.getString("value");
                if (value != null) {
                    SectionType type = SectionType.valueOf(rs.getString("type"));
                    executeSection(type, value, resume);
                }
            }
            return null;
        });
        return resume;
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("get all sorted");
        Map<String, Resume> resumes = sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                resumeMap.put(uuid, new Resume(uuid, rs.getString("full_name")));
            }
            return resumeMap;
        });
        executeQualities(CONTACTS, resumes);
        executeQualities(SECTIONS, resumes);
        return new ArrayList<>(resumes.values());
    }

    @Override
    public int size() {
        LOG.info("size");
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contacts (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO sections (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, AbstractSection> e: r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue() + "\n");
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void executeQualities(String table, Map<String, Resume> resumes) {
        sqlHelper.execute("SELECT * FROM " + table, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                String value = rs.getString("value");
                if (type != null && value != null) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    if (table.equals(CONTACTS)) {
                        resume.addContact(ContactType.valueOf(type), value);
                    } else if (table.equals(SECTIONS)) {
                        SectionType sectionType = SectionType.valueOf(type);
                        executeSection(sectionType, value, resume);
                    }
                }
            }
            return null;
        });
    }

    private void executeSection(SectionType sectionType, String value, Resume resume) {
        if (sectionType.equals(PERSONAL) || sectionType.equals(OBJECTIVE)) {
            resume.addSection(sectionType, new SimpleLineSection(value));
        } else if (sectionType.equals(ACHIEVEMENT) || sectionType.equals(QUALIFICATIONS)) {
            resume.addSection(sectionType, new BulletedListSection(Arrays.asList(value.split("\n"))));
        }
    }
}
