package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final boolean isExist(Object resume) {
        return storage.containsValue(resume);
    }

    @Override
    public final void updateToStorage(Object resume, Resume r) {
        storage.replace(((Resume) resume).getUuid(), r);
    }

    @Override
    public final void saveToStorage(Resume r, Object resume) {
        storage.put(((Resume) resume).getUuid(), r);
    }

    @Override
    public final Resume getFromStorage(Object resume) {
        return storage.get(((Resume) resume).getUuid());
    }

    @Override
    public final void deleteFromStorage(Object resume) {
        storage.values().remove(resume);
    }

    public final List<Resume> getCopyStorage() {
        return new ArrayList<>(storage.values());
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return new Resume(uuid);
    }
}
