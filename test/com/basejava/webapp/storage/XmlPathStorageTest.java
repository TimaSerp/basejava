package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.ObjectStreamSerializer;
import com.basejava.webapp.storage.serializer.XmlStreamSerializer;

import static com.basejava.webapp.storage.AbstractStorageTest.STORAGE_DIR;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new XmlStreamSerializer()));
    }
}
