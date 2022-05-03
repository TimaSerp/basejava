package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String FULLNAME_1 = "name1";
    private static final Resume RESUME_1 = new Resume(UUID_1, FULLNAME_1);

    private static final String UUID_2 = "uuid2";
    private static final String FULLNAME_2 = "name2";
    private static final Resume RESUME_2 = new Resume(UUID_2, FULLNAME_2);

    private static final String UUID_3 = "uuid3";
    private static final String FULLNAME_3 = "name3";
    private static final Resume RESUME_3 = new Resume(UUID_3, FULLNAME_3);

    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_4 = "name4";
    private static final Resume RESUME_4 = new Resume(UUID_4, FULLNAME_4);

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
    void update() throws Exception {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        assertSame(newResume, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(RESUME_4);
        });
    }

    @Test
    void save() throws Exception {
        storage.save(RESUME_4);
        assertSame(RESUME_4, storage.get(UUID_4));
        assertSize(4);
    }

    @Test
    void saveExist() throws Exception {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void get() throws Exception {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(RESUME_4.getUuid()));
    }

    @Test
    void getNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void delete() throws Exception {
        storage.delete(UUID_1);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
        assertSize(2);
    }

    @Test
    void deleteNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    void getAllSorted() throws Exception {
        List<Resume> checkList = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        assertIterableEquals(checkList, storage.getAllSorted());
    }

    @Test
    void size() throws Exception {
        assertSize(3);
    }

    private void assertSize(int size){
        assertEquals(size, storage.size());
    }
}
