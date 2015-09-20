package com.varunl.concurrency;

/**
 * This example will show how to use wait() and notify().
 *
 * Let's modify the counting problem statement. Now we want that thread1 only updates the value of count if it's odd and
 * thread2 updates the value of count if it's even.
 */
public class Example5 {
    private int count = 0;
    private Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Example5 ex = new Example5();
        ex.runExample();
    }

    public void runExample() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    synchronized (lock) {
                        // Increments count only if it's odd.
                        while (count % 2 == 0) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        count++;
                        System.out.println("Thread1 incremented count to " + count);
                        lock.notify();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    synchronized (lock) {
                        // Increments count only if it's even.
                        while (count % 2 == 1) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        count++;
                        System.out.println("Thread2 incremented count to " + count);
                        lock.notify();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Expected value of count: 2000, Actual count: " + count);
    }

}
