package com.varunl.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This example is a modification of the counting problem, solved using explicit locks.
 * - Thread1 will only increment count if it's odd
 * - Thread2 will only incrememt count if it's even
 */
public class Example7 {
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Example7 ex = new Example7();
        ex.runExample();
    }

    public void runExample() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    lock.lock();
                    // Wait till the variable is not odd.
                    while (count % 2 == 0) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    count++;
                    System.out.println("Thread1 incremented count to " + count);
                    condition.signal();
                    lock.unlock();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    lock.lock();
                    // Wait till the condition is not even.
                    while (count % 2 == 1) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    count++;
                    System.out.println("Thread2 incremented count to " + count);
                    condition.signal();
                    lock.unlock();
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
