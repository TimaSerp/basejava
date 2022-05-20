package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialize_strategy.ObjectStreamStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamStorage()));
    }
}
