/**
 * Array based storage for Resumes
 */

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private String uuid;
    private int rIndexMax = 0;

    void clear() {
        Arrays.fill(storage, 0, (rIndexMax), null);
        rIndexMax = 0;
    }

    void save(Resume r) {
        storage[rIndexMax] = r;
        rIndexMax++;
    }

    Resume get(String uuid) {
        if (checkExist()) {
            for (int i = 0; i < rIndexMax; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    return storage[i];
                }
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < rIndexMax; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                storage[i].setUuid(storage[rIndexMax - 1].getUuid());
                storage[rIndexMax - 1].setUuid(null);
                break;
            }
        }
        rIndexMax--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] storageCopy = Arrays.copyOf(storage, rIndexMax);
        return storageCopy;
    }

    int size() {
        return rIndexMax;
    }

    boolean checkExist() {
        if (rIndexMax == 0) {
            System.out.println("Error: there are no resume in your array");
            return false;
        } else {
            return true;
        }
    }
}
