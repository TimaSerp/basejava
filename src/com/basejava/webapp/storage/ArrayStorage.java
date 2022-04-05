package com.basejava.webapp.storage;
/**
 * Array based storage for Resumes
 */

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size = 0;

    public void update(Resume r) {
        if (checkExist(r.getUuid())) {
            storage[getIndex(r.getUuid())] = r;
        }
    }

    private boolean checkExist(String uuid) {
        if (size == 0) {
            System.out.println("Error: there are no resume in your storage");
            return false;
        } else if (getIndex(uuid) < storage.length){
            return true;
        }
        System.out.println("Error: no resume with uuid " + uuid + " found!");
        return false;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return storage.length;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (checkExistOrFull(r.getUuid())) {
            storage[size] = r;
            size++;
        }
    }

    private boolean checkExistOrFull(String uuid) {
        if (getIndex(uuid) < storage.length) {
            System.out.println("Error: resume with uuid " + uuid + " is already exist");
            return false;
        } else if (size == storage.length - 1) {
            System.out.println("Error: your storage is overflow");
            return false;
        }
        return true;
    }

    public Resume get(String uuid) {
        if (checkExist(uuid)) {
            return storage[getIndex(uuid)];
        }
        return null;
    }


    public void delete(String uuid) {
        if (checkExist(uuid)) {
            storage[getIndex(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
