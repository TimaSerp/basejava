package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.basejava.webapp.ResumeTestData.fillResume;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Users\\AnTi\\basejava\\storage");

    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String FULLNAME_1 = "name1";
    private static final Resume RESUME_1 = fillResume(UUID_1, FULLNAME_1);

    private static final String UUID_2 = "uuid2";
    private static final String FULLNAME_2 = "name2";
    private static final Resume RESUME_2 = fillResume(UUID_2, FULLNAME_2);

    private static final String UUID_3 = "uuid3";
    private static final String FULLNAME_3 = "name3";
    private static final Resume RESUME_3 = fillResume(UUID_3, FULLNAME_3);

    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_4 = "name4";
    private static final Resume RESUME_4 = fillResume(UUID_4, FULLNAME_4);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    void update() {
        Resume newResume = new Resume(UUID_1, FULLNAME_1);
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(RESUME_4);
        });
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertSize(4);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void get() {
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    void getAllSorted() {
        List<Resume> checkList = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        assertEquals(3, checkList.size());
        assertIterableEquals(checkList, storage.getAllSorted());
    }

    @Test
    void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}
