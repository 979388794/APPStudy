package com.MyStudy.JetPackTest.LifecycleTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import android.os.Bundle;
import android.util.Log;

import com.MyStudy.Basic_control_view.R;

public class LifeCycleActivity extends AppCompatActivity {
    IPresenter mypresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
//        getLifecycle().addObserver(new MyObserver());
//        getLifecycle().addObserver((new MyObserver2()));
//        getLifecycle().addObserver((new MyObserver3()));
        getLifecycle().addObserver((new MyObserver4()));

        mypresenter = new MyPresenter();
        //多个接口 + 工厂设计模式
        getLifecycle().addObserver(mypresenter);
    }

    public class MyObserver3 implements DefaultLifecycleObserver {
        //DefaultLifecycleObserver是对LifecycleObserver的二次封装，为了用户好用
        String Tag = "Henry";

        @Override
        public void onCreate(@NonNull LifecycleOwner owner) {
            DefaultLifecycleObserver.super.onCreate(owner);
            Log.d(Tag, "DefaultLifecycleObserver--------onCreate");
        }

        @Override
        public void onStop(@NonNull LifecycleOwner owner) {
            DefaultLifecycleObserver.super.onStop(owner);
            Log.d(Tag, "DefaultLifecycleObserver--------onStop");
        }

    }

}