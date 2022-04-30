package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object searchKey = findSearchKey(r.getUuid());
        checkNotExist(searchKey, r.getUuid());
        updateToStorage(searchKey, r);
    }

    public void save(Resume r) {
        checkExist(findSearchKey(r.getUuid()), r.getUuid());
        saveToStorage(r);
    }

    public Resume get(String uuid) {
        Object searchKey = findSearchKey(uuid);
        checkNotExist(searchKey, uuid);
        return getFromStorage(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = findSearchKey(uuid);
        checkNotExist(searchKey, uuid);
        deleteFromStorage(searchKey);
    }

    protected void checkNotExist(Object searchKey, String uuid){
        boolean checkNotExist;
        try {
            checkNotExist = (int) searchKey < 0;
        } catch (ClassCastException | NullPointerException e) {
            checkNotExist = Objects.equals(searchKey, null);
        }
        if (checkNotExist) {
            throw new NotExistStorageException(uuid);
        }
    }


    protected void checkExist(Object searchKey, String uuid) {
        boolean checkExist;
        try {
            checkExist = (int) searchKey >= 0;
        } catch (ClassCastException | NullPointerException e) {
            checkExist = !Objects.equals(searchKey, null);
        }
        if (checkExist) {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract Object findSearchKey(String uuid);

    protected abstract void updateToStorage(Object searchKey, Resume r);

    protected abstract void saveToStorage(Resume r);

    protected abstract Resume getFromStorage(Object searchKey);

    protected abstract void deleteFromStorage(Object searchKey);
}
