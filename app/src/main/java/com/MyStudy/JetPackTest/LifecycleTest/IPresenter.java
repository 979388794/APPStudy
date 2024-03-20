package com.MyStudy.JetPackTest.LifecycleTest;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author: henry.xue
 * @date: 2024-03-20
 */
public interface IPresenter extends DefaultLifecycleObserver {

    @Override
    default void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
    }

    @Override
    default void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
    }
}
