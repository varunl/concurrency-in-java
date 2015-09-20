package com.varunl.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Using executor service to solve the counting problem.
 */
public class Example8 {
    private int count = 0;
    private final Object lock = new Object();
    private final ExecutorService taskExecutor;

    public Example8(ExecutorService taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public static void main(String[] args) throws InterruptedException {
        Example8 ex = new Example8(Executors.newFixedThreadPool(3));
        ex.runExample();
    }

    public void runExample() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    incrementCount();
                }
            });
        }
        taskExecutor.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("Expected value of count: 10000, Actual count: " + count);
    }

    private void incrementCount() {
        for (int i = 0; i < 1000; i++) {
            synchronized (lock) {
                count++;
            }
        }
    }
}
