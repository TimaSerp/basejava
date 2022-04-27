package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        updateToStorage(r, index);
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        if (getIndex(uuid) >= 0) {
            throw new ExistStorageException(uuid);
        }
        saveToStorage(r);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getFromStorage(index);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteFromStorage(index);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void updateToStorage(Resume r, int index);

    protected abstract void saveToStorage(Resume r);

    protected abstract Resume getFromStorage(int index);

    protected abstract void deleteFromStorage(int index);
}
