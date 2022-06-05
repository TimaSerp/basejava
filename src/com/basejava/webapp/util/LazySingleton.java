package com.basejava.webapp.util;

public class LazySingleton {
    volatile private static LazySingleton INSTANCE;

    double sin = Math.sin(13);

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }
}
