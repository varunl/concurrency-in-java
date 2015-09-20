package com.varunl.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Try the counting problem using explicit locks instead of synchronized.
 */
public class Example6 {
    private int count = 0;
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Example6 ex = new Example6();
        ex.runExample();
    }

    public void runExample() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    lock.lock();
                    count++;
                    lock.unlock();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    lock.lock();
                    count++;
                    lock.unlock();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Expected value of count: 20000, Actual count: " + count);
    }
}
