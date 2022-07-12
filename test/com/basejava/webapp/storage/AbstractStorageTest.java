package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.basejava.webapp.ResumeTestData.fillResume;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String FULLNAME_1 = "name1";
    private static final Resume RESUME_1 = fillResume(UUID_1, FULLNAME_1);
//    private static final Resume RESUME_1 = new Resume(UUID_1, FULLNAME_1);

    private static final String UUID_2 = "uuid2";
    private static final String FULLNAME_2 = "name2";
    private static final Resume RESUME_2 = fillResume(UUID_2, FULLNAME_2);
//    private static final Resume RESUME_2 = new Resume(UUID_2, FULLNAME_2);

    private static final String UUID_3 = "uuid3";
    private static final String FULLNAME_3 = "name3";
    private static final Resume RESUME_3 = fillResume(UUID_3, FULLNAME_3);
//    private static final Resume RESUME_3 = new Resume(UUID_3, FULLNAME_3);

    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_4 = "name4";
    private static final Resume RESUME_4 = fillResume(UUID_4, FULLNAME_4);
//    private static final Resume RESUME_4 = new Resume(UUID_4, FULLNAME_4);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume newResume = fillResume(UUID_1, FULLNAME_1);
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(RESUME_4);
        });
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertSize(4);
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    public void get() {
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    public void getAllSorted() {
        List<Resume> checkList = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        assertEquals(3, checkList.size());
        assertIterableEquals(checkList, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}
