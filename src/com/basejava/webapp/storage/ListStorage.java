package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    private static final Comparator<Resume> FULLNAME_COMPARATOR = (o1, o2) -> o1.getFullName().compareTo(o2.getFullName());
    private static final Comparator<Resume> UUID_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());
    private static final Comparator<Resume> RESUME_COMPARATOR = FULLNAME_COMPARATOR.thenComparing(UUID_COMPARATOR);

    @Override
    public final boolean isExist (Object index) {
        return (int) index >= 0;
    }
    @Override
    public final void updateToStorage(Object index, Resume r) {
        storage.set((int) index, r);
    }

    @Override
    public final void saveToStorage(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    public final Resume getFromStorage(Object index) {
        return storage.get((int) index);
    }

    @Override
    public final void deleteFromStorage(Object index) {
        storage.remove((int) index);
    }

    public final List<Resume> getAllSorted() {
        List<Resume> copyStorage = storage;
        copyStorage.sort(RESUME_COMPARATOR);
        return copyStorage;
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected Object findSearchKey(String uuid) { return storage.indexOf(new Resume(uuid)); }
}
