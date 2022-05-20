package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private SerializeStrategy serializeStrategy;

    protected FileStorage(File directory, SerializeStrategy serializeStrategy) {
        Objects.requireNonNull(directory, "directory must be not null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable or writable");
        }
        this.directory = directory;
        this.serializeStrategy = serializeStrategy;
    }

    public void setSerializeStrategy(SerializeStrategy serializeStrategy) {
        this.serializeStrategy = serializeStrategy;
    }

    @Override
    protected List<Resume> getCopyStorage() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);

        }
        List<Resume> list = new ArrayList<>();
        for (File file : directory.listFiles()) {
            list.add(getFromStorage(file));
        }
        return list;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateToStorage(File file, Resume r) {
        try {
            serializeStrategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected void saveToStorage(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        updateToStorage(file, r);
    }

    @Override
    protected Resume getFromStorage(File file) {
        try {
            return serializeStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void deleteFromStorage(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();

        if (files!= null) {
            for (File file : files) {
                deleteFromStorage(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();

        if(list == null) {
            throw new StorageException("Directory read error", null);
        }
        return directory.list().length;
    }
}
