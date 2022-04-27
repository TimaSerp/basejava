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
    public final void updateToStorage(Resume r, int index) {
        storage.set(index, r);
    }

    @Override
    public final void saveToStorage(Resume r) {
        storage.add(r);
    }

    @Override
    public final Resume getFromStorage(int index) {
        return storage.get(index);
    }

    @Override
    public final void deleteFromStorage(int index) {
        storage.remove(index);
    }

    public final Resume[] getAll() {
        Resume[] cloneList = new Resume[storage.size()];
        return storage.toArray(cloneList);
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) { return storage.indexOf(new Resume(uuid)); }
}
