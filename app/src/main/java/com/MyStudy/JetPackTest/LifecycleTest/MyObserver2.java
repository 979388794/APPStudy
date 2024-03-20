package com.MyStudy.JetPackTest.LifecycleTest;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author: henry.xue
 * @date: 2024-03-19
 */
public class MyObserver2 implements DefaultLifecycleObserver {
    //DefaultLifecycleObserver是对LifecycleObserver的二次封装，为了用户好用
    String Tag = "Henry";

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);
        Log.d(Tag, "DefaultLifecycleObserver--------onCreate");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        Log.d(Tag, "DefaultLifecycleObserver--------onStart");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onResume(owner);
        Log.d(Tag, "DefaultLifecycleObserver--------onResume");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        Log.d(Tag, "DefaultLifecycleObserver--------onStop");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onPause(owner);
        Log.d(Tag, "DefaultLifecycleObserver--------onPause");
    }
}
