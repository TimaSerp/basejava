package com.basejava.webapp.storage; /**
 * Array based storage for Resumes
 */

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;
    private int count; // for iteration

    public void update(Resume r) {
        if (checkExist(r.getUuid())) {
            storage[count] = r;
            return;
        }
    }

    private boolean checkExist(String uuid) {
        if (size == 0) {
            System.out.println("Error: there are no resume in your storage");
            return false;
        } else if (checkMatch(uuid)){
            return true;
        }
        System.out.println("Error: no resume with uuid " + uuid + " found!");
        return false;
    }

    private boolean checkMatch(String uuid) {
        for (count = 0; count < size; count++) {
            if (storage[count].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (checkMatchOrFull(r.getUuid())) {
            storage[size] = r;
            size++;
        }
    }

    private boolean checkMatchOrFull(String uuid) {
        if (checkMatch(uuid)) {
            System.out.println("Error: resume with uuid " + uuid + " is already exist");
            return false;
        } else if (size == 9999) {
            System.out.println("Error: your storage is overflow");
            return false;
        }
        return true;
    }

    public Resume get(String uuid) {
        if (checkExist(uuid)) {
            return storage[count];
        }
        return null;
    }


    public void delete(String uuid) {
        if (checkExist(uuid)) {
            storage[count] = storage[size - 1];
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
