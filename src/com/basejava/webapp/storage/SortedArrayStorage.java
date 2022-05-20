package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    public void saveToArray(Resume r, int index) {
        int indexIdx = -index - 1;
        System.arraycopy(storage, indexIdx, storage, indexIdx + 1, size - indexIdx);
        storage[indexIdx] = r;
    }

    @Override
    public void fillDeletedElement(int index) {
        if (size - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}