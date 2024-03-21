package com.MyStudy.JetPackTest.LiveDataTest.weixindemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.MyStudy.JetPackTest.LiveDataTest.myLiveData;

public class MyService extends Service {
    String TAG="Henry";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10000;i++){
                    Log.d(TAG,"消息铃声--------------");
                    myLiveData.getInfo1().postValue("---------消息内容"+i);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}