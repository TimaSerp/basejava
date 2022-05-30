package com.basejava.webapp;

public class Deadlock {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("start " + Thread.currentThread().getName());
            synchronized (lock1) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {}
            }
            System.out.println("finish " + Thread.currentThread().getName());
        }, "The first Thread");

        Thread thread2 = new Thread(() -> {
            System.out.println("start " + Thread.currentThread().getName());
            synchronized (lock2) {
                synchronized (lock1) {}
            }
            System.out.println("finish " + Thread.currentThread().getName());
        }, "The second Thread");

        thread1.start();
        thread2.start();
    }
}
