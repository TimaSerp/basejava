package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final void updateElement(Resume r, int index) {
        storage.set(index, r);
    }

    @Override
    public final void saveElement(Resume r) {
        storage.add(r);
    }

    @Override
    public final Resume getElement(int index) {
        return storage.get(index);
    }

    @Override
    public final void deleteElement(String uuid, int index) {
        storage.remove(get(uuid));
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
