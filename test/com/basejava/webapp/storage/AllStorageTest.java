package com.basejava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.*;

@RunWith(Suite.class)
@SuiteClasses({
//        ArrayStorageTest.class,
//        FileStorageTest.class,
//        JsonPathStorageTest.class,
//        ListStorageTest.class,
//        MapResumeStorageTest.class,
//        MapUuidStorageTest.class,
//        PathStorageTest.class,
        SqlStorageTest.class,
//        SortedArrayStorageTest.class,
//        XmlPathStorageTest.class
})
public class AllStorageTest {
}
