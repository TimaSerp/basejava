package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    public void update(Resume r) {
        Object searchKey = getSearchKey(r.getUuid());
        checkNotExist(searchKey, r.getUuid());
        updateToStorage(searchKey, r);
    }

    public void save(Resume r) {
        checkExist(getSearchKey(r.getUuid()), r.getUuid());
        saveToStorage(r);
    }

    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        checkNotExist(searchKey, uuid);
        return getFromStorage(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        checkNotExist(searchKey, uuid);
        deleteFromStorage(searchKey);
    }

    protected abstract void checkNotExist(Object searchKey, String uuid);

    protected abstract void checkExist(Object searchKey, String uuid);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void updateToStorage(Object searchKey, Resume r);

    protected abstract void saveToStorage(Resume r);

    protected abstract Resume getFromStorage(Object searchKey);

    protected abstract void deleteFromStorage(Object searchKey);
}
