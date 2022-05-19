package com.basejava.webapp;

import java.io.File;

public class FilePathPrinter {
    public static void main(String[] args) {
        String filePath = ".";
        doRecursion(new File(filePath));
    }

    public static void doRecursion(File file) {
        if (file.isDirectory()) {
            try {
                if (file.list().length != 0) {
                    for (File f : file.listFiles()) {
                        doRecursion(f);
                    }
                }
            } catch (NullPointerException e) {
                throw new RuntimeException("Null pointer exception", e);
            }
        } else {
            System.out.println(file.getName());
        }
    }
}
