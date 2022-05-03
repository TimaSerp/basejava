package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

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

    public List<Resume> getAllSorted() {
        List<Resume> copyStorage = getCopyStorage();
        copyStorage.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return copyStorage;
    }

    protected abstract List<Resume> getCopyStorage();

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }


    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
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
