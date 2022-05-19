package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final boolean isExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    public final void updateToStorage(String key, Resume r) {
        storage.replace(key, r);
    }

    @Override
    public final void saveToStorage(Resume r, String key) {
        storage.put(key, r);
    }

    @Override
    public final Resume getFromStorage(String key) {
        return storage.get(key);
    }

    @Override
    public final void deleteFromStorage(String key) {
        storage.remove(key);
    }

    public final List<Resume> getCopyStorage() {
        return new ArrayList<>(storage.values());
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }
}
