package com.henry.diagnosisTest.utils;

import android.util.Log;

public class LogUtils {

    private static String versionName;
    private static String TAG_FLAG = ":";

    public static void setVersionName(String name) {
        versionName = name;
    }

    private String getVersionName(){
        return versionName;
    }
    public static void d(String TAG,String msg){
        String tag = TAG +  TAG_FLAG + versionName;
        Log.d(tag,msg);
    }

    public static void e(String TAG,String msg){
        String tag = TAG +  TAG_FLAG + versionName;
        Log.e(tag,msg);
    }

    public static void i(String TAG,String msg){
        String tag = TAG +  TAG_FLAG + versionName;
        Log.i(tag,msg);
    }

    public static void v(String TAG,String msg){
        String tag = TAG +  TAG_FLAG + versionName;
        Log.v(tag,msg);
    }
}
