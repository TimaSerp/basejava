package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    public final void updateToStorage(Integer index, Resume r) {
        storage[index] = r;
    }

    @Override
    public final void saveToStorage(Resume r, Integer index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveToArray(r, index);
        size++;
    }

    @Override
    public final Resume getFromStorage(Integer index) {
        return storage[index];
    }

    @Override
    public final void deleteFromStorage(Integer index) {
        fillDeletedElement(index);
        storage[size - 1] = null;
        size--;
    }

    public List<Resume> getCopyStorage() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    public int size() {
        return size;
    }

    protected abstract void saveToArray(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}
