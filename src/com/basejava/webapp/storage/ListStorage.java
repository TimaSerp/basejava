package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final void updateToStorage(Object index, Resume r) {
        storage.set((int) index, r);
    }

    @Override
    public final void saveToStorage(Resume r) {
        storage.add(r);
    }

    @Override
    public final Resume getFromStorage(Object index) {
        return storage.get((int) index);
    }

    @Override
    public final void deleteFromStorage(Object index) {
        storage.remove((int) index);
    }

    public final Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected Object findSearchKey(String uuid) { return storage.indexOf(new Resume(uuid)); }
}
