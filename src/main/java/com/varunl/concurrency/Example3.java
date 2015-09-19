package com.varunl.concurrency;

/**
 * The section of code for which we want to ensure that the access is not concurrent is called the critical section.
 * In our previous example, count was in the critical section.
 *
 * Let's see how we can solve this problem by using Java's locking mechanism.
 */
public class Example3 {
    private int count = 0;
    private final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Example3 ex = new Example3();
        ex.runExample();
    }

    public void runExample() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    synchronized (lock) {
                        count++;
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    synchronized (lock) {
                        count++;
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
