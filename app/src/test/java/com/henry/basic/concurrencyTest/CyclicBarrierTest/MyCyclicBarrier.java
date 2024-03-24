package com.henry.basic.concurrencyTest.CyclicBarrierTest;

import java.util.concurrent.CyclicBarrier;

/**
 * @author: henry.xue
 * @date: 2024-03-19
 */
public class MyCyclicBarrier {

    public static void test1() {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("All threads have reached the barrier, let's continue!");
        });

        for (int i = 1; i <= 3; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("Thread " + threadId + " is ready.");
                    barrier.await(); // 等待其他线程到达屏障
                    System.out.println("Thread " + threadId + " go!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        test2();
    }


    public static void test2() {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("All threads have reached the barrier, let's continue!");
        });

        for (int i = 1; i <= 3; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("Thread " + threadId + " is ready.");
                    barrier.await(); // 等待其他线程到达屏障
                    System.out.println("Thread " + threadId + " go!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        barrier.reset();
        for (int i = 10; i <= 19; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("Thread " + threadId + " is ready.");
                    barrier.await(); // 等待其他线程到达屏障
                    System.out.println("Thread " + threadId + " go!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


}
