package com.henry.basic.concurrencyTest.CountDownLatchTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: henry.xue
 * @date: 2024-03-17
 */
public class MyCountDownLatch {


    /**
     * @author: 公众号：java金融
     */
    public static void test1() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    //所有请求都阻塞在这，等待
                    countDownLatch.await();
                    // 调用测试接口
                    System.out.println(Thread.currentThread().getName() + "开始执行……");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println(Thread.currentThread().getName() + "主线程开始睡眠……");
        // 让请求都准备好
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + "主线程睡眠完成……");
        // 让所有请求统一请求
        countDownLatch.countDown();
    }


    public static void main(String[] args) throws InterruptedException {
        test4();
    }


    /**
     * @author: 公众号：java金融
     */

    public static void test2() throws InterruptedException {
        int count = 3;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final int index = i;
            new Thread(() -> {
                try {
                    Thread.sleep(1000 + ThreadLocalRandom.current().nextInt(1000));
                    System.out.println("finish" + index + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();// 主线程在阻塞，当计数器==0，就唤醒主线程往下执行。
        System.out.println("主线程:在所有任务运行完成后，进行结果汇总");
    }


    public static void test3() {
        final CountDownLatch latch = new CountDownLatch(3);

        Runnable task = () -> {
            System.out.println("Task is running..." + Thread.currentThread().getName());
            latch.countDown();
            System.out.println("Task is completed." + Thread.currentThread().getName());
        };

        // 启动3个线程执行任务
        for (int i = 0; i < 3; i++) {
            new Thread(task).start();
        }

        try {
            latch.await(); // 等待所有任务完成
            System.out.println("All tasks are completed. Proceed with main task.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test4() {
        final CountDownLatch latch = new CountDownLatch(1);

        Runnable receiveOrderTask = () -> {
            System.out.println("Step 1: Receive order");
            latch.countDown();
        };

        Runnable validateOrderTask = () -> {
            try {
                latch.await();
                System.out.println("Step 2: Validate order");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable payOrderTask = () -> {
            try {
                latch.await();
                System.out.println("Step 3: Pay order");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable shipOrderTask = () -> {
            try {
                latch.await();
                System.out.println("Step 4: Ship order");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 启动4个线程分别执行订单处理步骤
        new Thread(receiveOrderTask).start();
        new Thread(validateOrderTask).start();
        new Thread(payOrderTask).start();
        new Thread(shipOrderTask).start();
    }


}



