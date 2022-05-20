package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialize_strategy.ObjectStreamStorage;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamStorage()));
    }
}
