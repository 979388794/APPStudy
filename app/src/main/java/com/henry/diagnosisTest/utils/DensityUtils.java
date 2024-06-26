package com.henry.diagnosisTest.utils;

/**
 * @author: henry.xue
 * @date: 2024-06-17
 */

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class DensityUtils {
    private DensityUtils()

    {
        /* cannot be instantiated */

        throw new UnsupportedOperationException("cannot be instantiated");

    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return dp 值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp的值
     * @param context 上下文
     * @return px的值
     */
    public static float sp2px(float spValue, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度
     */
    public static int getScreenHeight(@NonNull Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        //  float density1 = dm.density;
        //int width3 = dm.widthPixels;
        return dm.heightPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度
     */
    public static int getScreenWidth(@NonNull Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        //  float density1 = dm.density;
        //int width3 = dm.widthPixels;
        return dm.widthPixels;
    }




    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */

    public static int dp2px(Context context, float dpVal)

    {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,

                dpVal, context.getResources().getDisplayMetrics());

    }


    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */

    public static int sp2px(Context context, float spVal)

    {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,

                spVal, context.getResources().getDisplayMetrics());

    }


    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */

    public static float px2dp(Context context, float pxVal)

    {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (pxVal / scale);

    }


    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */

    public static float px2sp(Context context, float pxVal)

    {

        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);

    }















}
