package com.basejava.webapp;

public class Deadlock {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("start " + Thread.currentThread().getName());
            getDeadLock(lock1, lock2);
            System.out.println("finish " + Thread.currentThread().getName());
        }, "The first Thread");

        Thread thread2 = new Thread(() -> {
            System.out.println("start " + Thread.currentThread().getName());
            getDeadLock(lock2, lock1);
            System.out.println("finish " + Thread.currentThread().getName());
        }, "The second Thread");

        thread1.start();
        thread2.start();
    }

    private static void getDeadLock(Object firstLock, Object secondLock) {
        synchronized (firstLock) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (secondLock) {}
        }
    }
}
