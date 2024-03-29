package com.basejava.webapp;

import com.basejava.webapp.storage.SqlStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected static final File PROPS = new File(getHomeDir() + "config\\resumes.properties");
    private Properties props = new Properties();
    private SqlStorage sqlStorage;
    private File storageDir;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream("C:\\Users\\AnTi\\basejava — копия\\config\\resumes.properties")) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"),
                    props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public SqlStorage getSqlStorage() {
        return sqlStorage;
    }

    public File getStorageDir() {
        return storageDir;
    }

    private static File getHomeDir() {
        String homeDir = System.getProperty("homeDir");
        File file = new File(homeDir == null ? "." : homeDir);
        if (!file.isDirectory()) {
            throw new IllegalStateException(file + "is not directory");
        }
        return file;
    }
}
