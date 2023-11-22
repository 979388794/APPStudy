package com.example.custom_view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.HomeScreen.R;

public class MyTextview extends View {
    private float mOriginalX;
    private float mOriginalY;
    private float mOriginalRawX;
    private float mOriginalRawY;
    private View view;
    public  MyTextview mtv;



    private float mX;
    private float mY;
    float moveX;
    float moveY;
    public MyTextview(@NonNull Context context) {
        super(context);
    }

    public MyTextview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
      //  init();

        Log.d("xuejie"," 左上角横坐标为"+getX()+"左上角纵坐标为"+getY());
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.activity_custom_view, null);
        mtv=(MyTextview)view.findViewById(R.id.view_test);
    }

    public MyTextview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);//两点间距离公式
    }
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN://手指按下
//                //按下的时候获取手指触摸的坐标
//                Log.d("xuejie","ACTION_DOWN");
//                mX =  event.getRawX();
//                mY =  event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE://手指滑动
//                //滑动时计算偏移量
//                Log.d("xuejie","ACTION_MOVE");
//                moveX = event.getRawX() - mX;
//                moveY = event.getRawY() - mY;
//                //随手指移动
//                setTranslationX(moveX);
//                setTranslationY(moveY);
//                break;
//            case MotionEvent.ACTION_UP://手指松开
//                Log.d("xuejie","ACTION_UP");
//                break;
//        }
////        return super.onTouchEvent(event);
//        return true;
//    }



    //getx此时代表触摸的图片左侧距离屏幕左侧的x轴距离，图片位置不动，该值不变
    //event.getRawX();代表触摸图片上的点距离屏幕左侧的距离，点变值变   触摸图片左侧时，与getx值相同
    //event.getx:触摸点距离自身view左边界的距离

    //getY此时代表触摸的图片顶部距离app屏幕顶部的y轴距离，图片位置不动，该值不变
    //event.getRawY();代表触摸图片上的点距离屏幕顶部的距离，点变值变   触摸图片顶侧时，与getY值相差app与屏幕一块的距离
    //event.getY:触摸点距离自身view顶部边界的距离

    //getTranslationX:偏移量，初始值为0




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mOriginalX = getX();
                mOriginalY = getY();
                mOriginalRawX = event.getRawX();
                mOriginalRawY = event.getRawY();
//                Log.d("xuejie"," "+"x轴中心坐标为"+getWidth() * 0.5+"y轴中心坐标为"+ getHeight() * 0.5);
//                Log.d("xuejie"," "+"x轴中心坐标为"+getPivotX()+"y轴中心坐标为"+ getPivotY());
//                Log.d("xuejie","ACTION_DOWN");

                Log.d("xuejie","X"+" "+getTranslationX());
                Log.d("xuejie","mOriginalX"+" "+mOriginalX);
                Log.d("xuejie","mOriginalRawX"+" "+mOriginalRawX);
                Log.d("xuejie","mOriginalY"+" "+mOriginalY);
                Log.d("xuejie","mOriginalRawY"+" "+mOriginalRawY);
                Log.d("xuejie","event.getY"+" "+event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                setX(mOriginalX + event.getRawX() - mOriginalRawX);
                setY(mOriginalY + event.getRawY() - mOriginalRawY);
                Log.d("xuejie","ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }











}
