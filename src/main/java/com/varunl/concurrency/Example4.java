package com.varunl.concurrency;

/**
 * This is an example of a deadlock. We will continue using the counting problem from previous examples.
 */
public class Example4 {
    private int count = 0;
    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Example4 ex = new Example4();
        ex.runExample();
    }

    public void runExample() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    synchronized (lockA) {
                        System.out.println("In Thread1. Acquired lockA, will attempt to acquire lockB. Count: " + count);
                        synchronized (lockB) {
                            count++;
                        }
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                // Adding this sleep to show how this code will not deadlock for the first 10 milliseconds.
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 10000; i++) {
                    synchronized (lockB) {
                        System.out.println("In Thread2. Acquired lockB, will attempt to acquire lockA. Count: " + count);
                        synchronized (lockA) {
                            count++;
                        }
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Expected value of count: 20000, Actual value: " + count);
    }
}
