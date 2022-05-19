package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public void update(Resume r) {
        LOG.info("Update " + r);
        updateToStorage(getExistedSearchKey(r.getUuid()), r);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        saveToStorage(r, getNotExistedSearchKey(r.getUuid()));
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return getFromStorage(getExistedSearchKey(uuid));
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        deleteFromStorage(getExistedSearchKey(uuid));
    }

    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> copyStorage = getCopyStorage();
        Collections.sort(copyStorage);
        return copyStorage;
    }

    protected abstract List<Resume> getCopyStorage();

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }


    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(SK searchKey);

    protected abstract SK findSearchKey(String uuid);

    protected abstract void updateToStorage(SK searchKey, Resume r);

    protected abstract void saveToStorage(Resume r, SK searchKey);

    protected abstract Resume getFromStorage(SK searchKey);

    protected abstract void deleteFromStorage(SK searchKey);
}
