package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serialize_strategy.SerializeStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializeStrategy serializeStrategy;

    protected PathStorage(String dir, SerializeStrategy serializeStrategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must be not null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + " is not directory or not writable");
        }
        this.serializeStrategy = serializeStrategy;
    }

    @Override
    protected List<Resume> getCopyStorage() {
        List<Resume> list = new ArrayList<>();
        checkReadError().forEach(path -> list.add(getFromStorage(path)));
        return list;
    }

    private Stream<Path> checkReadError() {
        Stream<Path> paths = null;
        try {
            paths = Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return paths;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void updateToStorage(Path path, Resume r) {
        try {
            serializeStrategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path write error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void saveToStorage(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path.normalize(), path.getFileName().toString(), e);
        }
        updateToStorage(path, r);
    }

    @Override
    protected Resume getFromStorage(Path path) {
        try {
            return serializeStrategy.doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteFromStorage(Path path) throws StorageException {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    public void clear() {
        checkReadError().forEach(this::deleteFromStorage);
    }

    @Override
    public int size() {
        return getCopyStorage().toArray().length;
    }
}
