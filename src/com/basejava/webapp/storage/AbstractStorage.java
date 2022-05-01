package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        updateToStorage(getExistedSearchKey(r.getUuid()), r);
    }

    public void save(Resume r) {
        saveToStorage(r, getNotExistedSearchKey(r.getUuid()));
    }

    public Resume get(String uuid) {
        return getFromStorage(getExistedSearchKey(uuid));
    }

    public void delete(String uuid) {
        deleteFromStorage(getExistedSearchKey(uuid));
    }

    protected Object getExistedSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }


    protected Object getNotExistedSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        System.out.println(searchKey);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object findSearchKey(String uuid);

    protected abstract void updateToStorage(Object searchKey, Resume r);

    protected abstract void saveToStorage(Resume r, Object searchKey);

    protected abstract Resume getFromStorage(Object searchKey);

    protected abstract void deleteFromStorage(Object searchKey);
}
