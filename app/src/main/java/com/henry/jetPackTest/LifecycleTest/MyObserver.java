package com.henry.jetPackTest.LifecycleTest;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author: henry.xue
 * @date: 2024-03-19
 */
public class MyObserver implements LifecycleObserver {
    String Tag = "Henry";


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Log.d(Tag, "---------------onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)//可见连接
    public void connectListener() {
        Log.d(Tag, "connectListener-----------run");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)//不可见断开连接
    public void disconnectListener() {
        Log.d(Tag, "disconnectListener-----------run");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Log.d(Tag, "---------------onStop");
    }


}
