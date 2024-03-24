package com.henry.basic.concurrencyTest.ThreadPoolTest;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: henry.xue
 * @date: 2024-03-15
 */
@RunWith(JUnit4.class)
public class testThreadPoolFactory implements ThreadFactory {

    private AtomicInteger threadIdx = new AtomicInteger(0);

    private String threadNamePrefix;

    public testThreadPoolFactory(String Prefix) {
        threadNamePrefix = Prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadNamePrefix + "-xxljob-" + threadIdx.getAndIncrement());
        return thread;
    }

    /**
     * 可缓存无界线程池测试
     * 当线程池中的线程空闲时间超过60s则会自动回收该线程，核心线程数为0
     * 当任务超过线程池的线程数则创建新线程。线程池的大小上限为Integer.MAX_VALUE，
     * 可看做是无限大。
     */

    public static void cacheThreadPoolTest() {
        // 创建可缓存的无界线程池，可以指定线程工厂，也可以不指定线程工厂
        ExecutorService executorService = Executors.newCachedThreadPool(new testThreadPoolFactory("cachedThread"));
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                        System.out.println(Thread.currentThread().getName());
                    }
            );
        }
    }


    public static void main(String[] args) {
//        cachedThreadPoolTest();
//        fixedThreadPoolTest();
//        ScheduledExecutorServiceTest();
//        singleThreadExecutorTest();
        ThreadPoolExecutorTest();
    }

    public static void cachedThreadPoolTest() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(() -> {
            System.out.println("Task 1");
        });
        cachedThreadPool.execute(() -> {
            System.out.println("Task 2");
        });
    }


    public static void fixedThreadPoolTest() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        fixedThreadPool.execute(() -> {
            System.out.println("Task 1");
        });
        fixedThreadPool.execute(() -> {
            System.out.println("Task 2");
        });
    }

    public static void ScheduledExecutorServiceTest() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        scheduledThreadPool.schedule(() -> {
            System.out.println("Scheduled Task 1");
        }, 3, TimeUnit.SECONDS);
        scheduledThreadPool.scheduleAtFixedRate(() -> {
            System.out.println("Scheduled Task 2");
        }, 0, 5, TimeUnit.SECONDS);
    }

    public static void singleThreadExecutorTest() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(() -> {
            System.out.println("Task 1");
        });
        singleThreadExecutor.execute(() -> {
            System.out.println("Task 2");
        });
    }


    public static void ThreadPoolExecutorTest() {
        // 1. 创建线程池
        // 创建时，通过配置线程池的参数，从而实现自己所需的线程池
        Executor threadPool = new ThreadPoolExecutor(
                12,
                15,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
        // 注：在Java中，已内置4种常见线程池，下面会详细说明

// 2. 向线程池提交任务：execute（）
        // 说明：传入 Runnable对象
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
              //  ... // 线程执行任务
                System.out.println("Task 2");
            }
        });

// 3. 关闭线程池shutdown()
        ((ThreadPoolExecutor) threadPool).shutdown();

        // 关闭线程的原理
        // a. 遍历线程池中的所有工作线程
        // b. 逐个调用线程的interrupt（）中断线程（注：无法响应中断的任务可能永远无法终止）

        // 也可调用shutdownNow（）关闭线程：threadPool.shutdownNow（）
        // 二者区别：
        // shutdown：设置 线程池的状态 为 SHUTDOWN，然后中断所有没有正在执行任务的线程
        // shutdownNow：设置 线程池的状态 为 STOP，然后尝试停止所有的正在执行或暂停任务的线程，并返回等待执行任务的列表
        // 使用建议：一般调用shutdown（）关闭线程池；若任务不一定要执行完，则调用shutdownNow（）

    }

}

