package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final boolean isExist (Object index) {
        return (int) index >= 0;
    }
    @Override
    public final void updateToStorage(Object index, Resume r) {
        storage.set((int) index, r);
    }

    @Override
    public final void saveToStorage(Resume r, Object searchKey) {
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

    public final List<Resume> getCopyStorage() {
        return new ArrayList<>(storage);
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected Object findSearchKey(String uuid) { return storage.indexOf(new Resume(uuid)); }
}
