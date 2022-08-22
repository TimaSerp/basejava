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
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((SimpleLineSection) entry.getValue()).getItems());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(((BulletedListSection) entry.getValue()).getItems(), dos, item -> {
                            dos.writeUTF(item);
                        });
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeWithException(((Experience) entry.getValue()).getItems(), dos, org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            String url = org.getHomePage().getUrl();
                            dos.writeBoolean(url == null);
                            if (!(url == null)) {
                                dos.writeUTF(url);
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
            readWithException(dis, () -> r.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                AbstractSection section = null;
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new SimpleLineSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        section = new BulletedListSection(readAndGetList(dis, dis::readUTF));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        section = new Experience(
                                readAndGetList(dis, () -> new Organization(
                                        new Link(dis.readUTF(), dis.readBoolean() ? null : dis.readUTF()),
                                        readAndGetList(dis, () -> new Organization.Position(
                                                readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readBoolean() ? null : dis.readUTF()
                                        ))
                                )));
                        break;
                    default:
                        throw new IllegalStateException();
                }
                r.addSection(sectionType, section);
            });
            return r;
        }
    }

    @FunctionalInterface
    interface ReaderWithException {
        void read() throws IOException;
    }

    private static void readWithException(DataInputStream dis, ReaderWithException reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    @FunctionalInterface
    interface ListFillingReader<T> {
        T read() throws IOException;
    }

    private <T> List<T> readAndGetList(DataInputStream dis, ListFillingReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
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
