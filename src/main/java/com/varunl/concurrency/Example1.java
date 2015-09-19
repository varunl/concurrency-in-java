package com.varunl.concurrency;


/**
 * This examaple shows that if 2 threads try to update a variable, then you will not have the correct output without
 * using some sychronization techniques.
 */
public class Example1 {
    private int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Example1 ex = new Example1();
        ex.runExample();
    }

    public void runExample() throws InterruptedException {
        // Create 2 threads who update the value of the counter.
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    count++;
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    count++;
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
