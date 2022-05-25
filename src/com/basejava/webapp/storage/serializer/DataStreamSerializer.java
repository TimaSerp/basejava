package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            writeSections(dos, r);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume r = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String name = dis.readUTF();
                r.addContact(contactType, name);
            }
            readSections(dis, r);
            return r;
        } catch (EOFException e) {
            throw new StorageException("End of file", e);
        }
    }

    private void writeSections(DataOutputStream dos, Resume r) throws IOException {
        dos.writeInt(r.getSections().size());
        for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
            dos.writeUTF(entry.getKey().name());
            if (entry.getKey().ordinal() <= 1) {
                dos.writeUTF(entry.getValue().toString());
            }
            if (entry.getKey().ordinal() == 2 || entry.getKey().ordinal() == 3) {
                BulletedListSection section = (BulletedListSection) entry.getValue();
                dos.writeInt(section.getItems().size());
                for (String item : section.getItems()) {
                    dos.writeUTF(item);
                }
            }
            if (entry.getKey().ordinal() >= 4) {
                writeExperience(dos, (Experience) entry.getValue());
            }
        }
    }

    private void writeExperience(DataOutputStream dos, Experience section) throws IOException {
        dos.writeInt(section.getOrganizations().size());
        for (Organization org : section.getOrganizations()) {
            dos.writeUTF(org.getHomePage().getName());
            dos.writeBoolean(org.getHomePage().getUrl() == null);
            if (!(org.getHomePage().getUrl() == null)) {
                dos.writeUTF(org.getHomePage().getUrl());
            }
            dos.writeInt(org.getPositions().size());
            for (Organization.Position pos : org.getPositions()) {
                writeLocalDate(dos, pos.getDateStart());
                writeLocalDate(dos, pos.getDateFinish());
                dos.writeUTF(pos.getPost());
                dos.writeBoolean(pos.getDefinition() == null);
                if (!(pos.getDefinition() == null)) {
                    dos.writeUTF(pos.getDefinition());
                }
            }
        }
    }

    private void readSections(DataInputStream dis, Resume r) throws IOException {
        AbstractSection section = null;
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch(sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    section = new SimpleLineSection(dis.readUTF());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> lines = new ArrayList<>();
                    int size2 = dis.readInt();
                    for (int j = 0; j < size2; j++) {
                        lines.add(dis.readUTF());
                    }
                    section = new BulletedListSection(lines);
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    section = new Experience(readExperience(dis));
                    break;
                default:
            }
            r.addSection(sectionType, section);
        }
    }

    private List<Organization> readExperience(DataInputStream dis) throws IOException {
        List<Organization> orgs = new ArrayList<>();
        int size = dis.readInt();
        for (int j = 0; j < size; j++) {
            String name = dis.readUTF();
            String url = dis.readBoolean() ? null : dis.readUTF();
            List<Organization.Position> posts = new ArrayList<>();
            int size2 = dis.readInt();
            for (int k = 0; k < size2; k++) {
                LocalDate dateStart = readLocalDate(dis);
                LocalDate dateFinish = readLocalDate(dis);
                String post = dis.readUTF();
                String definition = dis.readBoolean() ? null : dis.readUTF();
                posts.add(new Organization.Position(dateStart, dateFinish, post, definition));
            }
            orgs.add(new Organization(new Link(name, url), posts));
        }
        return orgs;
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonthValue());
        dos.writeInt(ld.getDayOfMonth());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }
}
