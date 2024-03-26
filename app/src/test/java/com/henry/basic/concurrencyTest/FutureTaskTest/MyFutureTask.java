package com.henry.basic.concurrencyTest.FutureTaskTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author: henry.xue
 * @date: 2024-03-26
 */
public class MyFutureTask {

    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                // 模拟一个耗时的计算任务
                Thread.sleep(2000);
                return 42;
            }
        };

        Future<Integer> future = executor.submit(task);
        System.out.println("Task submitted");

        try {
            // 获取任务的执行结果，会阻塞当前线程直到任务完成
            int result = future.get();
            System.out.println("Task result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }


    public static void test2() {
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                // 模拟一个耗时的计算任务
                Thread.sleep(2000);
                return 42;
            }
        };

        FutureTask<Integer> futureTask = new FutureTask<>(task);

        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println("Task submitted");

        try {
            // 获取任务的执行结果，会阻塞当前线程直到任务完成
            int result = futureTask.get();
            System.out.println("Task result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


}
