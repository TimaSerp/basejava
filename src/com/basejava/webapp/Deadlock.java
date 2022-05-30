package com.basejava.webapp;

public class Deadlock {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    private static volatile int count;

    public static void main(String[] args) {
        getDeadLock(lock1, lock2);
        getDeadLock(lock2, lock1);
    }

    private static void getDeadLock(Object firstLock, Object secondLock) {
        Thread thread = new Thread(() -> {
            System.out.println("start " + Thread.currentThread().getName());
            synchronized (firstLock) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (secondLock) {
                }
            }
            System.out.println("finish " + Thread.currentThread().getName());
        });
        thread.start();
    }
}
