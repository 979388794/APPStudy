package com.henry.jetPackTest.LifecycleTest;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author: henry.xue
 * @date: 2024-03-20
 */
public class MyPresenter implements IPresenter {

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        IPresenter.super.onStart(owner);

    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        IPresenter.super.onStop(owner);

    }

}
