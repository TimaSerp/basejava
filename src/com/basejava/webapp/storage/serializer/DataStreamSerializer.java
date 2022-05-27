package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer<T> implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeWithException(r.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeWithException(r.getSections().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(entry.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        BulletedListSection section = (BulletedListSection) entry.getValue();
                        writeWithException(section.getItems(), dos, item -> {
                            dos.writeUTF(item);
                        });
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        Experience experience = (Experience) entry.getValue();
                        writeWithException(experience.getOrganizations(), dos, org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeBoolean(org.getHomePage().getUrl() == null);
                            if (!(org.getHomePage().getUrl() == null)) {
                                dos.writeUTF(org.getHomePage().getUrl());
                            }
                            writeWithException(org.getPositions(), dos, pos -> {
                                writeLocalDate(dos, pos.getDateStart());
                                writeLocalDate(dos, pos.getDateFinish());
                                dos.writeUTF(pos.getPost());
                                dos.writeBoolean(pos.getDefinition() == null);
                                if (!(pos.getDefinition() == null)) {
                                    dos.writeUTF(pos.getDefinition());
                                }
                            });
                        });

                }
            });

        }
    }

    @FunctionalInterface
    interface WriterWithException<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, WriterWithException<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume r = new Resume(dis.readUTF(), dis.readUTF());
            readWithException(dis, r, (resume) -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, r, (resume) -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                AbstractSection section = null;
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new SimpleLineSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> lines = new ArrayList<>();
                        readWithException(dis, r, (resume1) -> lines.add(dis.readUTF()));
                        section = new BulletedListSection(lines);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        section = new Experience(readAndGetList(dis, r, () -> {
                            String name = dis.readUTF();
                            String url = dis.readBoolean() ? null : dis.readUTF();
                            List<Organization.Position> posts = readAndGetList(dis, r, () -> {
                                LocalDate dateStart = readLocalDate(dis);
                                LocalDate dateFinish = readLocalDate(dis);
                                String post = dis.readUTF();
                                String definition = dis.readBoolean() ? null : dis.readUTF();
                                return new Organization.Position(dateStart, dateFinish, post, definition);
                            });
                            return new Organization(new Link(name, url), posts);
                        }));
                        break;
                }
                r.addSection(sectionType, section);
            });
            return r;
        }
    }

    @FunctionalInterface
    interface ReaderWithException {
        void read(Resume r) throws IOException;
    }

    private static void readWithException(DataInputStream dis, Resume r, ReaderWithException reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read(r);
        }
    }

    @FunctionalInterface
    interface ListFillingReader<T> {
        T read() throws IOException;
    }

    private static <T> List<T> readAndGetList(DataInputStream dis, Resume r, ListFillingReader reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add((T) reader.read());
        }
        return list;
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
