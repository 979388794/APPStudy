package com.henry.jetPackTest.workManagerTest;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.henry.basic.R;

/**
 * @author: henry.xue
 * @date: 2024-03-27
 */
public class workManagerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityworkmanager);
    }

    /**
     * 测试后台任务
     * @return
     */
    public void test1(View view) {
        // OneTimeWorkRequest 单个 一次的
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(mainWorker1.class).build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

    /**
     * 数据互相传递
     * @param view
     */
    public void test2(View view) {


    }

    public void test3(View view) {

    }

    public void test4(View view) {

    }

    public void test5(View view) {

    }

    public void test6(View view) {

    }

}