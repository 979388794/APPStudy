package com.henry.basic;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.henry.diagnosisTest.base.ActivityManager;
import com.henry.diagnosisTest.handler.MyCrashHandler;
import com.henry.diagnosisTest.utils.DDSManager;
import com.henry.diagnosisTest.utils.LogUtils;


public class MyApplication extends Application {

    public static MyApplication instance;
    public static boolean isFirsted = true;

    /**
     * 初始化 MyCrashHandler 实现崩溃监测
     * 注册与Activity生命周期相关的回调。
     *
     * 初始化 DDSManager
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MyCrashHandler handler = new MyCrashHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
        registerActivityLifecycleCallbacks(ActivityManager.getInstance());
        DDSManager.getInstance().init(this);
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (null != packageInfo) {
            String versionName = packageInfo.versionName;
            LogUtils.setVersionName(versionName);
            Log.d("MyApplication", "versionName = " + versionName);
        }
    }

    /**
     * 应用终止时销毁DDSManager里的内容
     * 解除与Activity生命周期相关的回调。
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        DDSManager.getInstance().reset();
        unregisterActivityLifecycleCallbacks(ActivityManager.getInstance());
    }
}
