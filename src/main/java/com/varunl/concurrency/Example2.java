package com.varunl.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * By using AtomicInteger, we can fix the problem in the previous example. AtomicInteger makes the increment operation
 * atomic.
 */
public class Example2 {
    private AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Example2 ex = new Example2();
        ex.runExample();
    }

    public void runExample() throws InterruptedException {
        // Create 2 threads who update the value of the counter.
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    count.incrementAndGet();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    count.incrementAndGet();
                }
            }
        });

        thread1.start();
        thread2.start();

        // Wait for the threads to finish.
        thread1.join();
        thread2.join();

        System.out.println("Expected value of count: 20000, Actual count: " + count);
    }
}
