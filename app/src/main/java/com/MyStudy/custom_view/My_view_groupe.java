package com.MyStudy.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class My_view_groupe extends ViewGroup {


    public My_view_groupe(Context context) {
        super(context);
    }

    public My_view_groupe(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public My_view_groupe(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public My_view_groupe(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        if (childCount == 0) {//如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
            setMeasuredDimension(0, 0);
        } else {
            //如果宽高都是包裹内容
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                //我们将高度设置为所有子View的高度相加，宽度设为子View中最大的宽度
                int height = getTotleHeight();
                int width = getMaxChildWidth();
                setMeasuredDimension(width, height);

            } else if (heightMode == MeasureSpec.AT_MOST) {//如果只有高度是包裹内容
                //宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
                setMeasuredDimension(widthSize, getTotleHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {//如果只有宽度是包裹内容
                //宽度设置为子View中宽度最大的值，高度设置为ViewGroup自己的测量值
                setMeasuredDimension(getMaxChildWidth(), heightSize);

            }
        }
    }
    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth)
                maxWidth = childView.getMeasuredWidth();
        }
        return maxWidth;
    }

    /***
     * 将所有子View的高度相加
     **/
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();

        }

        return height;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前的高度位置
        int curHeight = t;
        //将子View逐个摆放
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            //摆放子View，参数分别是子View矩形区域的左、上、右、下边
            child.layout(l, curHeight, l + width, curHeight + height);
            curHeight += height;
        }
    }

}
