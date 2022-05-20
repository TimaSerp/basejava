package com.basejava.webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamStorage()));
    }
}
