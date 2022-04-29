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
    public final void checkNotExist(Object searchKey, String uuid){
        if (!storage.containsKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public final void checkExist(Object searchKey, String uuid){
        if (storage.containsKey(searchKey)) {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public final void updateToStorage(Object searchKey, Resume r) {
        storage.replace((String) searchKey, r);
    }

    @Override
    public final void saveToStorage(Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    public final Resume getFromStorage(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    public final void deleteFromStorage(Object searchKey) {
        storage.remove((String) searchKey);
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
    protected Object getSearchKey(String uuid) {
        return uuid;
    }
}
