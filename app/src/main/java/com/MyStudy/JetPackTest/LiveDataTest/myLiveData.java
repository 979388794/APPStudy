package com.MyStudy.JetPackTest.LiveDataTest;

import androidx.lifecycle.MutableLiveData;

/**
 * @author: henry.xue
 * @date: 2024-03-20
 */
public class myLiveData {
    private static MutableLiveData<String> info1;
    public static MutableLiveData<String> getInfo1() {
        if (info1 == null) {
            info1 = new MutableLiveData<>();
        }
        return info1;
    }

}
