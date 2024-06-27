package com.henry.basic;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.henry.diagnosisTest.ActivityManager;
import com.henry.diagnosisTest.MyCrashHandler;
import com.henry.diagnosisTest.utils.DDSManager;
import com.henry.diagnosisTest.utils.LogUtils;


public class MyApplication extends Application {

    public static MyApplication instance;
    public static boolean isFirsted = true;


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
            Log.d("HotFixApplication", "versionName = " + versionName);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DDSManager.getInstance().reset();
        unregisterActivityLifecycleCallbacks(ActivityManager.getInstance());
    }
}
