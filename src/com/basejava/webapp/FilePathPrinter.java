package com.basejava.webapp;

import java.io.File;

public class FilePathPrinter {
    private static int count = -1;

    public static void main(String[] args) {
        String filePath = "C:\\Users\\AnTi\\basejava\\src\\com\\basejava\\webapp";
        doRecursion(new File(filePath));
    }

    public static void doRecursion(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                count++;
                for (int i = 0; i < count; i++) {
                    System.out.print("   ");
                }
                System.out.println("Dir: " + file.getName());
                if (file.list().length != 0) {
                    for (File f : file.listFiles()) {
                        doRecursion(f);
                    }
                }
                System.out.println("");
                count--;
            } else {
                for (int i = 0; i <= count; i++) {
                    System.out.print("   ");
                }
                System.out.println("File: " + file.getName());
            }
        }
    }
}
