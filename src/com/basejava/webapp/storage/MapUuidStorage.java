package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage{
    private Map<String, Resume> storage = new LinkedHashMap<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final boolean isExist (Object key) {
        return storage.containsKey((String) key);
    }

    @Override
    public final void updateToStorage(Object key, Resume r) {
        storage.replace((String) key, r);
    }

    @Override
    public final void saveToStorage(Resume r, Object key) {
        storage.put((String) key, r);
    }

    @Override
    public final Resume getFromStorage(Object key) {
        return storage.get((String) key);
    }

    @Override
    public final void deleteFromStorage(Object key) {
        storage.remove((String) key);
    }

    public final List<Resume> getCopyStorage() {
        return new ArrayList<>(storage.values());
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return uuid;
    }
}
