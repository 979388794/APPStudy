package com.example.PowerControlTest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    private static final String TAG = "xuejie";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //子线程中执行
        Log.i(TAG, "onHandleIntent");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(3000);
                String extra = intent.getStringExtra("msg");
                Log.i(TAG, "onHandleIntent:" + extra);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //调用completeWakefulIntent来释放唤醒锁。
        WLWakefulReceiver.completeWakefulIntent(intent);
    }
}
