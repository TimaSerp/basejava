package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        updateElement(r, index);
    }

    public final void save(Resume r) {
        String uuid = r.getUuid();
        if (getIndex(uuid) >= 0) {
            throw new ExistStorageException(uuid);
        }
        saveElement(r);
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(index);
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteElement(uuid, index);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void updateElement(Resume r, int index);

    protected abstract void saveElement(Resume r);

    protected abstract Resume getElement(int index);

    protected abstract void deleteElement(String uuid, int index);
}
