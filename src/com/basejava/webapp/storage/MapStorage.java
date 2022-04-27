package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    protected Map<String, Resume> storage = new LinkedHashMap<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final void updateToStorage(Resume r, int index) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    public final void saveToStorage(Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    public final Resume getFromStorage(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    public final void deleteFromStorage(int index, String uuid) {
        storage.remove(uuid);
    }

    public final Resume[] getAll() {
        ArrayList<Resume> cloneMap = new ArrayList<>(storage.values());
        Resume[] cloneList = new Resume[storage.size()];
        return cloneMap.toArray(cloneList);
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        int checkExist = (storage.containsKey(uuid) ? 1 : 0) - 1;
        return checkExist;
    }
}
