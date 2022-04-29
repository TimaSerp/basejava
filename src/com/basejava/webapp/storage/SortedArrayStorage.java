package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveToArray(Resume r) {
        int index = -(int)getSearchKey(r.getUuid()) - 1;
        if (index == size) {
            storage[size] = r;
            return;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    public void fillDeletedElement(int index) {
        if (size - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}