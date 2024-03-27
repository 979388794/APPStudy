package com.henry.jetPackTest.workManagerTest;

import android.content.Context;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author: henry.xue
 * @date: 2024-03-27
 */
public class mainWorker2 extends Worker {

    private static final String TAG = "Henry";

    public mainWorker2(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    // 后台任务并且是异步的 (原理：线程池执行Runnable)
    @Override
    public Result doWork() {
        Log.d(TAG, "MainWorker1 doWork :run started ...");
        try {
            Thread.sleep(8000); // 睡眠8s
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(); // 本次任务失败
        } finally {
            Log.d(TAG, "MainWorker1 doWork :run ended ...");
        }
        return Result.success(); // 本次任务成功
    }
}