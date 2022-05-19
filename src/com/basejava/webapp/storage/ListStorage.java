package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final boolean isExist(Integer index) {
        return index != null;
    }

    @Override
    public final void updateToStorage(Integer index, Resume r) {
        storage.set(index, r);
    }

    @Override
    public final void saveToStorage(Resume r, Integer searchKey) {
        storage.add(r);
    }

    @Override
    public final Resume getFromStorage(Integer index) {
        return storage.get(index);
    }

    @Override
    public final void deleteFromStorage(Integer index) {
        storage.remove((int) index);
    }

    public final List<Resume> getCopyStorage() {
        return new ArrayList<>(storage);
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }
}
