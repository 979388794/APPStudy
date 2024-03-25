package com.henry.jetPackTest.viewModelTest;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author: henry.xue
 * @date: 2024-03-24
 */
public class MyViewModel extends AndroidViewModel {
    Context context;
    public int number = 0;

    public MyViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    /**
     * 释放资源
     */
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}