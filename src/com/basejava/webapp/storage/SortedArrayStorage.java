package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        String uuid = r.getUuid();
        if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else if (getIndex(uuid) >= 0) {
            System.out.println("Resume " + uuid + " already exist");
        } else if (size == 0) {
            storage[size] = r;
            size++;
        } else {
            int index = -getIndex(uuid) - 1;
            if (index == size) {
                storage[size] = r;
                size++;
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
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            for (int i = index; i < size; i++) {
                storage[i] = storage[i + 1];
            }
            storage[size - 1] = null;
            size--;
        }
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}