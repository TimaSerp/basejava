package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialize_strategy.ObjectStreamStorage;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}
