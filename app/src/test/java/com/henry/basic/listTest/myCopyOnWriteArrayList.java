package com.henry.basic.listTest;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: henry.xue
 * @date: 2024-04-11
 */
public class myCopyOnWriteArrayList {


    public static void main(String[] args) {

        startTest();

    }
    private static final Integer THREAD_POOL_MAX_SIZE = 10;

    // 不支持并发
//    private static List<String> mList = new ArrayList<>();
    // 支持并发
    private static List<String> mList = new CopyOnWriteArrayList<>();

    private static void startTest() {
        // 初始化数据
        for (int i = 0; i < 10; i++) {
            mList.add("line:" + (i + 1) + "data");
        }
        System.out.println("------------初始化完成--------------------------");
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_MAX_SIZE);

        // 读写并发测试
        for (int i = 0; i < THREAD_POOL_MAX_SIZE; i++) {
            // 读任务立即执行
            executorService.execute(() -> {
                for (String item : mList) {
                    System.out.println(Thread.currentThread().getName() + "数据：" + item);
                }
            });
            final int final1 = i + 10;
            // 写任务立即执行
            executorService.execute(() -> {
                mList.add("写线程添加数据" + final1 + "..............");
            });
        }
    }

}
