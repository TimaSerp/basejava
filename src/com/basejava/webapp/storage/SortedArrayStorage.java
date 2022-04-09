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
        Resume buff1;
        Resume buff2 = null;
        for (int j = index; j <= size; j++) {
            buff1 = storage[j];
            storage[j] = buff2;
            buff2 = buff1;
        }
        storage[index] = r;
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}