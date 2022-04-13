package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void saveResume(Resume r) {
        int index = -getIndex(r.getUuid()) - 1;
        if (index == size) {
            storage[size] = r;
            return;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}