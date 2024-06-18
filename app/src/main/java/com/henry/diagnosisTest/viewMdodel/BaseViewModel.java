package com.henry.diagnosisTest.viewMdodel;

/**
 * @author: henry.xue
 * @date: 2024-06-17
 */

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;


/**
 * 基础视图模型
 *
 * @param <T>
 */
public class BaseViewModel<T> extends ViewModel {

    public String TAG = getClass().getSimpleName();

    /**
     * 弱引用缓存接口类
     */
    private WeakReference<T> mNavigator;

    /**
     * 获取接口类
     *
     * @return 接口类
     */
    public T getNavigator() {
        return mNavigator.get();
    }

    /**
     * 设置接口类
     *
     * @param navigator 接口类
     */
    public void setNavigator(T navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    /**
     * 初始化数据
     *
     * @param intent 传递数据的载体
     */
    public void initData(@NonNull Intent intent) {
    }

    public void resetListener() {

    }

    @Override
    public void onCleared() {
        super.onCleared();
        if (mNavigator != null) {
            resetListener();
            mNavigator.clear();
        }
    }


}