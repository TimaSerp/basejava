package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void checkNotExist(Object searchKey, String uuid){
        if ((int) searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public final void checkExist(Object searchKey, String uuid){
        if ((int) searchKey >= 0) {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public final void updateToStorage(Object searchKey, Resume r) {
        storage[(int) searchKey] = r;
    }

    @Override
    public final void saveToStorage(Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveToArray(r);
        size++;
    }

    @Override
    public final Resume getFromStorage(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    public final void deleteFromStorage(Object searchKey) {
        fillDeletedElement((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract void saveToArray(Resume r);

    protected abstract void fillDeletedElement(int index);
}
