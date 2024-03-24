package com.henry.jetPackTest.OkLiveDataBusTest;

/**
 * @author: henry.xue
 * @date: 2024-03-21
 */

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class OkLiveDataBusJava {
    //存放订阅者
    private static final Map<String, BusMutableLiveData<?>> bus = new HashMap<>();

    public synchronized static <T> BusMutableLiveData<T> with(String key, Class<T> type, boolean ishook) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutableLiveData<>(ishook));
        }
        return (BusMutableLiveData<T>) bus.get(key);
    }

    public static class BusMutableLiveData<T> extends MutableLiveData<T> {
        private boolean ishook;
        private BusMutableLiveData(boolean ishook) {
            this.ishook = ishook;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer<? super T> observer) {
            super.observe(owner, observer);
            if (ishook) {
                hook(observer);
                Log.d("Henry", " 启用hook");
            } else {
                Log.d("Henry", " 不启用hook");
            }
        }

        private void hook(Observer<? super T> observer) {
            try {
                Field mObserversField = LiveData.class.getDeclaredField("mObservers");
                mObserversField.setAccessible(true);
                Object mObserversObject = mObserversField.get(this);

                Class<?> mObserversClass = mObserversObject.getClass();
                Method get = mObserversClass.getDeclaredMethod("get", Object.class);
                get.setAccessible(true);
                Object invokeEntry = get.invoke(mObserversObject, observer);

                Object observerWrapper = null;
                if (invokeEntry != null && invokeEntry instanceof Map.Entry) {
                    observerWrapper = ((Map.Entry) invokeEntry).getValue();
                }
                if (observerWrapper == null) {
                    throw new NullPointerException("observerWrapper is null");
                }
                Log.d("Henry","属性是什么"+ observerWrapper.getClass());
                Class<?> superClass = observerWrapper.getClass().getSuperclass();
                Field mLastVersion = superClass.getDeclaredField("mLastVersion");
                mLastVersion.setAccessible(true);

                Field mVersion = LiveData.class.getDeclaredField("mVersion");
                mVersion.setAccessible(true);

                Object mVersionValue = mVersion.get(this);
                mLastVersion.set(observerWrapper, mVersionValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
