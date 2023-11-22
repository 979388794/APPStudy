package com.example.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class ZoomView extends View {
    private float mOriginalX; // 移动X
    private float mOriginalY; // 移动Y

    // 属性变量
    private float translationX; // 移动X
    private float translationY; // 移动Y
    private float scale = 1; // 伸缩比例
    private float rotation; // 旋转角度

    // 移动过程中临时变量

    private float actionX;
    private float actionY;
    private float spacing;
    private float degree;
    private int moveType; // 0=未选择，1=拖动，2=缩放

    public ZoomView(Context context) {
        this(context, null);
    }

    public ZoomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);//可以点击触摸
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:  //第一次按下
                moveType = 1;  //拖动标志
                mOriginalX = getX();
                mOriginalY = getY();
                actionX = event.getRawX();//代表触摸图片上的点距离屏幕左侧的距离，点变值变   触摸图片左侧时，与getx值相同
                actionY = event.getRawY();//代表触摸图片上的点距离屏幕顶部的距离，点变值变   触摸图片顶部时，与getY值相同
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                moveType = 2;
                spacing = getSpacing(event);
                degree = getDegree(event);
                Log.d("xuejie","degree="+degree);
                break;
            case MotionEvent.ACTION_MOVE:
                if (moveType == 1) {//拖动操作
                    setX(mOriginalX+event.getRawX()-actionX);
                    setY(mOriginalY+event.getRawY()-actionY);
                } else if (moveType == 2) {//
                    scale = scale * getSpacing(event) / spacing;
                    setScaleX(scale);
                    setScaleY(scale);
                    rotation = rotation + getDegree(event) - degree;
                    Log.d("xuejie","rotation="+rotation);
                    if (rotation > 360) {
                        rotation = rotation - 360;
                    }
                    if (rotation < -360) {
                        rotation = rotation + 360;
                    }
                    setRotation(rotation);
                }
                break;
            case MotionEvent.ACTION_UP:
                moveType = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                moveType = 1;//未选择标志
                break;
        }
        return super.onTouchEvent(event);
    }

    // 触碰两点间距离
    private float getSpacing(MotionEvent event) {
        //通过三角函数得到两点间的距离
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 取旋转角度
    private float getDegree(MotionEvent event) {
        //得到两个手指间的旋转角度
        double delta_x = event.getX(0) - event.getX(1);
        double delta_y = event.getY(0) - event.getY(1);
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}
