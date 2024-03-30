package com.henry.projectPractice.pagetransformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author: henry.xue
 * @date: 2024-03-30
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    public static final float MIN_SCALE = 0.8f;//原图片缩小0.8倍
    private static final float MIN_ALPHA = 0.6f;//透明度

    public void transformPage(View page, float position) {
        if (position < -1) {//[-Infinity,-1)左边显示出半个的page
            page.setAlpha(MIN_ALPHA);//设置page的透明度
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) {
            if (position < 0) {//(0,-1] 第一页向左移动
                if (position < -0.2f)//最大缩小到0.8倍
                    position = -0.2f;
                page.setAlpha(1f + position * 2);
                page.setScaleY(1f + position);
                page.setScaleX(1f + position);
            } else {//[1,0] 第二页向左移动 成currentItem
                if (position > 0.2)
                    position = 0.2f;
                page.setAlpha(1f - position * 2);
                page.setScaleY(1f - position);
                page.setScaleX(1f - position);
            }
        } else {//(1,+Infinity]右边显示出半个的page
            page.setAlpha(MIN_ALPHA);
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}