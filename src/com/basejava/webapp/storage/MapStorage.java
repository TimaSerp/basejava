package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    private Map<String, Resume> storage = new LinkedHashMap<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final void updateToStorage(Object key, Resume r) {
        storage.replace((String) key, r);
    }

    @Override
    public final void saveToStorage(Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    public final Resume getFromStorage(Object key) {
        return storage.get((String) key);
    }

    @Override
    public final void deleteFromStorage(Object key) {
        storage.remove((String) key);
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
    protected Object findSearchKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }
}
