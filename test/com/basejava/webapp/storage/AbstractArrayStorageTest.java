package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.getAll().length);
        assertEquals(0, storage.size());
    }

    @Test
    void update() throws Exception {
        Resume r4 = new Resume(UUID_1);
        storage.update(r4);
        assertSame(r4, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume(UUID_4));
        });
    }

    @Test
    void save() throws Exception {
        Resume r4 = new Resume(UUID_4);
        storage.save(r4);
        assertSame(r4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test
    void saveExist() throws Exception {
        assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume(UUID_3));
        });
    }

    @Test
    void saveOverflow() throws Exception {
        try {
            for (int i = 4; i <= 10000; i++) {
                storage.save(new Resume("" + i));
            }
        } catch (Exception e) {
            fail("Overflow is earlier than expected");
        }
        assertThrows(StorageException.class, () -> {
            storage.save(new Resume("" + 10001));
        });
    }

    @Test
    void get() throws Exception {
        Resume r4 = new Resume("uuid4");
        storage.save(r4);
        assertEquals(r4, storage.get("uuid4"));
    }

    @Test
    void getNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

    @Test
    void delete() throws Exception {
        assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_1);
            storage.get(UUID_1);
        });
    }

    @Test
    void deleteNotExist() throws Exception {
        assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_4);
        });
    }

    @Test
    void getAll() throws Exception {
        assertEquals(storage.get(UUID_1), storage.getAll()[0]);
        assertEquals(storage.get(UUID_2), storage.getAll()[1]);
        assertEquals(storage.get(UUID_3), storage.getAll()[2]);
    }

    @Test
    void size() throws Exception {
        assertEquals(3, storage.size());
    }
}