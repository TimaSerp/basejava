package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

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
        NotExistStorageException thrown = assertThrows(NotExistStorageException.class, () -> {
            storage.clear();
            storage.get(UUID_1);
        });
        assertTrue(thrown.getMessage().contains("Resume " + UUID_1 + " not exist"));
    }

    @Test
    void update() throws Exception {
        Resume r4 = new Resume(UUID_1);
        storage.update(r4);
        assertEquals(r4, storage.get(UUID_1));
    }

    @Test
    void save() throws Exception {
        ExistStorageException thrown = assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume("uuid4"));
            storage.save(new Resume("uuid4"));
        });
        assertTrue(thrown.getMessage().contains("Resume uuid4 already exist"));
    }

    @Test
    void overflow() throws Exception {
        try {
            for (int i = 4;  i <= 10000; i++) {
                storage.save(new Resume("" + i));
            }
        } catch(Exception e) {
            fail("Overflow is earlier than expected");
        }
        StorageException thrown = assertThrows(StorageException.class, () -> {
            storage.save(new Resume("" + 10001));
        });
        assertTrue(thrown.getMessage().contains("Storage overflow"));
    }

    @Test
    void get() throws Exception {
        Resume r4 = new Resume("uuid4");
        storage.save(r4);
        assertEquals(r4, storage.get("uuid4"));
    }

    @Test
    void getNotExist() throws Exception {
        NotExistStorageException thrown = assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
        assertTrue(thrown.getMessage().contains("Resume dummy not exist"));
    }

    @Test
    void delete() throws Exception {
        NotExistStorageException thrown = assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_1);
            storage.delete(UUID_1);
        });
        assertTrue(thrown.getMessage().contains("Resume " + UUID_1 + " not exist"));
    }

    @Test
    void getAll() throws Exception {
        assertEquals(3, storage.getAll().length);
    }

    @Test
    void size() throws Exception {
        assertEquals(3, storage.size());
    }
}