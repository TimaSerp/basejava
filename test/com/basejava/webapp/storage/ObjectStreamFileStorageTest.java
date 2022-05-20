package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialize_strategy.ObjectStreamStorage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}
