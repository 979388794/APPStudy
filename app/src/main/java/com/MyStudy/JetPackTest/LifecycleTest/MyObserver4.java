package com.MyStudy.JetPackTest.LifecycleTest;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author: henry.xue
 * @date: 2024-03-20
 */
public class MyObserver4 implements LifecycleEventObserver {
    String Tag = "Henry";

    @Override
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                Log.d(Tag, "initVideo");
                break;
            case ON_START:
                Log.d(Tag, "startPlay");
                break;
            case ON_RESUME:
                Log.d(Tag, "resumePlay");
                break;
            default:
                break;
        }
    }
}
