package com.henry.basic.concurrencyTest.SemaphoreTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author: henry.xue
 * @date: 2024-03-19
 */
public class MySemaphore {


    public static void main(String[] args) {
        test3();
    }

    public static void test1() {
        final Semaphore semaphore = new Semaphore(2);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("线程:" + Thread.currentThread().getName() + "获得许可:" + index);
                        TimeUnit.SECONDS.sleep(1);
                        semaphore.release();
                        System.out.println("允许TASK个数：" + semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();

    }


    /**
     * 上述代码创建了40个线程，但是只能允许有10个线程并发执行。
     * Semaphore的构造方法Semaphore（int permits）接受一个整型的数字，
     * 表示可用的许可证数量。Semaphore（10）表示允许10个线程获取许可证，也就是最大并发数是10。
     */
    public static void test2() {
        final int THREAD_COUNT = 40;
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
        Semaphore s = new Semaphore(10, true);

        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程" + Thread.currentThread().getName() + " 读取文件");
                        s.acquire();
                        System.out.println("线程" + Thread.currentThread().getName() + " 存储文件");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        s.release();
                    }
                }
            });
        }
        threadPool.shutdown();
    }

    /**
     * 假设有30个人在超市支付结算，只有3个结算窗口，代码实现逻辑如下
     */

    public static void test3() {
        // 排队总人数（请求总数）
        int clientTotal = 30;
        // 可同时结算商品的窗口数量（同时并发执行的线程数）
        int threadTotal = 3;

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire(1);
                    payment(count);
                    semaphore.release(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

    private static void payment(int i) throws InterruptedException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(Thread.currentThread().getName() + " 支付结算中" + formatter.format(date));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void test4() {
        Semaphore semaphore = new Semaphore(2); // 初始化一个许可证数量为2的 Semaphore
        // 创建多个线程尝试获取许可证
        for (int i = 1; i <= 5; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("Thread " + threadId + " acquired the permit.");
                    Thread.sleep(2000); // 模拟线程在访问资源
                    semaphore.release();
                    System.out.println("Thread " + threadId + " released the permit.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }


}
