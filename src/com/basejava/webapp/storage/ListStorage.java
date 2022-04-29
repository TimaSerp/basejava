package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final void checkNotExist(Object searchKey, String uuid){
        if ((int) searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public final void checkExist(Object searchKey, String uuid){
        if ((int) searchKey >= 0) {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public final void updateToStorage(Object searchKey, Resume r) {
        storage.set((int) searchKey, r);
    }

    @Override
    public final void saveToStorage(Resume r) {
        storage.add(r);
    }

    @Override
    public final Resume getFromStorage(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    public final void deleteFromStorage(Object searchKey) {
        storage.remove((int) searchKey);
    }

    public final Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected Object getSearchKey(String uuid) { return storage.indexOf(new Resume(uuid)); }
}
