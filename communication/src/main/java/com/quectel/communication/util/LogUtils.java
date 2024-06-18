package com.quectel.communication.util;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.quectel.communication.BuildConfig;


public final class LogUtils {
    /**
     * 私有构造函数
     */
    private LogUtils() {

    }

    /**
     * 错误日志打印
     *
     * @param object 调用打印日志的类
     * @param str    日志信息
     */
    public static void e(Object object, String str) {
        if (BuildConfig.DEBUG && object != null) {
            Log.e(object.getClass().getName(), str);
        }
    }

    /**
     * 警告日志打印
     *
     * @param object 调用打印日志的类
     * @param str    日志信息
     */
    public static void w(Object object, String str) {
        if (BuildConfig.DEBUG && object != null) {
            Log.w(object.getClass().getName(), str);
        }
    }

    /**
     * 普通日志打印
     *
     * @param object 调用打印日志的类
     * @param str    日志信息
     */
    public static void v(Object object, String str) {
        if (BuildConfig.DEBUG && object != null) {
            Log.v(object.getClass().getName(), str);
        }
    }

    /**
     * 调试日志打印
     *
     * @param object 调用打印日志的类
     * @param str    日志信息
     */
    public static void d(@NonNull Object object, String str) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(str)) {
            Log.d(object.getClass().getName(), str);
        }
    }

    /**
     * 信息日志打印
     *
     * @param object 调用打印日志的类
     * @param str    日志信息
     */
    public static void i(Object object, String str) {
        if (BuildConfig.DEBUG && object != null) {
            Log.i(object.getClass().getName(), str);
        }
    }
}
