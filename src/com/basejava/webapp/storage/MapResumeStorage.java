package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    public final void clear() {
        storage.clear();
    }

    @Override
    public final boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    public final void updateToStorage(Resume resume, Resume r) {
        storage.replace(resume.getUuid(), r);
    }

    @Override
    public final void saveToStorage(Resume r, Resume resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    public final Resume getFromStorage(Resume resume) {
        return resume;
    }

    @Override
    public final void deleteFromStorage(Resume resume) {
        storage.values().remove(resume);
    }

    public final List<Resume> getCopyStorage() {
        return new ArrayList<>(storage.values());
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }
}
