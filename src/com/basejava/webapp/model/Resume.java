package com.basejava.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;

    private HashMap<ContactType, String> contacts;
    private HashMap<SectionType, AbstractSection> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must be not null");
        Objects.requireNonNull(fullName, "fullName must be not null");
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public HashMap<ContactType, String> getContacts() {
        return contacts;
    }

    public void setContacts(HashMap<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public HashMap<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void setSections(HashMap<SectionType, AbstractSection> sections) {
        this.sections = sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!fullName.equals(resume.fullName)) return false;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + " (" + fullName + ")";
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    public String getResume() {
        StringBuilder sb = new StringBuilder();
        sb.append(fullName + "\n");
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            sb.append(entry.getKey().getTitle() + ": " + entry.getValue() + "\n");
        }
        for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
            sb.append(entry.getKey().getTitle() + "\n" + entry.getValue().toString());
        }
        return sb.toString();
    }
}