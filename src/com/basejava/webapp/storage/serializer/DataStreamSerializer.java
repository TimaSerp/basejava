package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static java.lang.Integer.parseInt;

public class DataStreamSerializer<T> implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeWithException(r.getContacts(), dos, new WriterWithException<Map.Entry<ContactType, String>>() {
                @Override
                public void write(Map.Entry<ContactType, String> entry, DataOutputStream dos) throws IOException {
                        dos.writeUTF(entry.getKey().name());
                        dos.writeUTF(entry.getValue());
                }
            });
            writeWithException(r.getSections(), dos, new WriterWithException<Map.Entry<SectionType, AbstractSection>>() {
                @Override
                public void write(Map.Entry<SectionType, AbstractSection> entry, DataOutputStream dos) throws IOException {
                    dos.writeUTF(entry.getKey().name());
                    switch(entry.getKey()) {
                        case PERSONAL:
                        case OBJECTIVE:
                            dos.writeUTF(entry.getValue().toString());
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            BulletedListSection section = (BulletedListSection) entry.getValue();
                            writeWithException(section.getItems(), dos, new WriterWithException<String>() {
                                @Override
                                public void write(String item, DataOutputStream dos) throws IOException {
                                    dos.writeUTF(item);
                                }
                            });
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            Experience experience = (Experience) entry.getValue();
                            writeWithException(experience.getOrganizations(), dos, new WriterWithException<Organization>() {
                                @Override
                                public void write(Organization org, DataOutputStream dos) throws IOException {
                                    dos.writeUTF(org.getHomePage().getName());
                                    dos.writeBoolean(org.getHomePage().getUrl() == null);
                                    if (!(org.getHomePage().getUrl() == null)) {
                                        dos.writeUTF(org.getHomePage().getUrl());
                                    }
                                    writeWithException(org.getPositions(), dos, new WriterWithException<Organization.Position>() {
                                        @Override
                                        public void write(Organization.Position pos, DataOutputStream dos) throws IOException {
                                            writeLocalDate(dos, pos.getDateStart());
                                            writeLocalDate(dos, pos.getDateFinish());
                                            dos.writeUTF(pos.getPost());
                                            dos.writeBoolean(pos.getDefinition() == null);
                                            if (!(pos.getDefinition() == null)) {
                                                dos.writeUTF(pos.getDefinition());
                                            }
                                        }
                                    });
                                }
                            });
                    }
                }
            });

        }
    }

    @FunctionalInterface
    interface WriterWithException<T> {
        void write(T t, DataOutputStream dos) throws IOException;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, WriterWithException writer) throws IOException {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(dos);
        Objects.requireNonNull(writer);
        dos.writeInt(collection.size());
        for (T t: collection) {
            writer.write(t, dos);
        }
    }

    private <K, V> void writeWithException(Map<K, V> map, DataOutputStream dos, WriterWithException writer) throws IOException {
        Objects.requireNonNull(map);
        Objects.requireNonNull(dos);
        Objects.requireNonNull(writer);
        dos.writeInt(map.size());
        for (Map.Entry entry: map.entrySet()) {
            writer.write(entry, dos);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume r = new Resume(dis.readUTF(), dis.readUTF());
            readWithException(dis, r, (dis1, resume) -> resume.addContact(ContactType.valueOf(dis1.readUTF()), dis.readUTF()));
            readWithException(dis, r, (dis1, resume) -> {
                SectionType sectionType = SectionType.valueOf(dis1.readUTF());
                AbstractSection section = null;
                switch(sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new SimpleLineSection(dis1.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> lines = new ArrayList<>();
                        readWithException(dis, r, (dis2, resume1) -> lines.add(dis2.readUTF()));
                        section = new BulletedListSection(lines);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        section = new Experience(readAndGetList(dis, r, new ListFillingReader<Organization>() {
                            @Override
                            public Organization read(DataInputStream dis, Resume r) throws IOException {
                                String name = dis.readUTF();
                                String url = dis.readBoolean() ? null : dis.readUTF();
                                List<Organization.Position> posts = readAndGetList(dis, r, new ListFillingReader<Organization.Position>() {
                                    @Override
                                    public Organization.Position read(DataInputStream dis, Resume r) throws IOException {
                                        LocalDate dateStart = readLocalDate(dis);
                                        LocalDate dateFinish = readLocalDate(dis);
                                        String post = dis.readUTF();
                                        String definition = dis.readBoolean() ? null : dis.readUTF();
                                        return new Organization.Position(dateStart, dateFinish, post, definition);
                                    }
                                });
                                return new Organization(new Link(name, url), posts);
                            }
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
        void read(DataInputStream dis, Resume r) throws IOException;
    }

    private static void readWithException(DataInputStream dis, Resume r, ReaderWithException reader) throws IOException {
        Objects.requireNonNull(dis);
        Objects.requireNonNull(r);
        Objects.requireNonNull(reader);
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read(dis, r);
        }
    }

    @FunctionalInterface
    interface ListFillingReader<T> {
        T read(DataInputStream dis, Resume r) throws IOException;
    }

    private static <T> List<T> readAndGetList(DataInputStream dis, Resume r, ListFillingReader reader) throws IOException {
        Objects.requireNonNull(dis);
        Objects.requireNonNull(r);
        Objects.requireNonNull(reader);
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add((T) reader.read(dis, r));
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
