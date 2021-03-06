package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    public void doWrite(Resume r, OutputStream os) throws IOException;

    public Resume doRead(InputStream is) throws IOException;
}
