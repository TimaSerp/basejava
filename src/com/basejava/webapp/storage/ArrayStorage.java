package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveToArray(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    public void fillDeletedElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}