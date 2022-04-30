package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        updateToStorage(findExistKey(r.getUuid()), r);
    }

    public void save(Resume r) {
        checkExistKey(r.getUuid());
        saveToStorage(r);
    }

    public Resume get(String uuid) {
        return getFromStorage(findExistKey(uuid));
    }

    public void delete(String uuid) {
        deleteFromStorage(findExistKey(uuid));
    }

    protected Object findExistKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        boolean checkNotExist = false;
        if (searchKey instanceof Integer) {
            checkNotExist = (int) searchKey < 0;
        } else if (searchKey instanceof String) {
            checkNotExist = Objects.equals(searchKey, "null");
        }
        if (checkNotExist) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }


    protected void checkExistKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        boolean checkExist = false;
        if (searchKey instanceof Integer) {
            checkExist = (int) searchKey >= 0;
        } else if (searchKey instanceof String) {
            checkExist = !Objects.equals(searchKey, "null");
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
