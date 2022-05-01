package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static int size = 0;

    private static final Comparator<Resume> FULLNAME_COMPARATOR = (o1, o2) -> o1.getFullName().compareTo(o2.getFullName());
    private static final Comparator<Resume> UUID_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());
    private static final Comparator<Resume> RESUME_COMPARATOR = FULLNAME_COMPARATOR.thenComparing(UUID_COMPARATOR);

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final boolean isExist (Object index) {
        return (int) index >= 0;
    }

    @Override
    public final void updateToStorage(Object index, Resume r) {
        storage[(int) index] = r;
    }

    @Override
    public final void saveToStorage(Resume r, Object index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveToArray(r, (int) index);
        size++;
    }

    @Override
    public final Resume getFromStorage(Object index) {
        return storage[(int) index];
    }

    @Override
    public final void deleteFromStorage(Object index) {
        fillDeletedElement((int) index);
        storage[size - 1] = null;
        size--;
    }

    public List<Resume> getAllSorted() {
        List<Resume> copyStorage = new ArrayList<>();
        Collections.addAll(copyStorage, Arrays.copyOf(storage, size));
        copyStorage.sort(RESUME_COMPARATOR);
        return copyStorage;
    }

    public int size() {
        return size;
    }

    protected abstract void saveToArray(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}
