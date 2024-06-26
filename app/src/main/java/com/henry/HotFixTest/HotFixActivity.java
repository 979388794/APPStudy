package com.henry.HotFixTest;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.henry.basic.R;

import java.lang.reflect.Method;

public class HotFixActivity extends AppCompatActivity {
    private static final String TAG = "HotFixActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Demo.test();
    }

    private void getVMStackLoader() {
        try {
            Class<?> cls = Class.forName("dalvik.system.VMStack");
            Method getCallingClassLoader = cls.getMethod("getCallingClassLoader");
            getCallingClassLoader.setAccessible(true);
            Object invoke = getCallingClassLoader.invoke(null);
            Log.i(TAG, "getVMStackLoader: " + invoke);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
