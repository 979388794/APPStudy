package com.henry.projectPractice.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.henry.basic.R;
import com.henry.projectPractice.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: henry.xue
 * @date: 2024-03-28
 */

public class GuideActivity extends AppCompatActivity {

    private ViewPager vp;
    private List<ImageView> imageViews;
    private int[] imgs = {R.drawable.y0, R.drawable.y1, R.drawable.y2, R.drawable.y3};
    private Button btn;
    private ImageView[] dotViews;
    private GuideAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        vp = findViewById(R.id.guide_vp);
        btn = findViewById(R.id.guide_btn);

        //初始化图片
        initImgs();
        //初始化底部圆点指示器
        initDots();
        adapter = new GuideAdapter(imageViews);
        vp.setAdapter(adapter);


//        vp.setPageTransformer(true,new DepthPageTransformer());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("isFirst", "1");
                editor.commit();
                Intent intent= new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotViews.length; i++) {
                    if (position == i) {
                        dotViews[i].setImageResource(R.drawable.guide_selector);
                    } else {
                        dotViews[i].setImageResource(R.drawable.guide_white);
                    }

                    if (position == dotViews.length - 1) {
                        btn.setVisibility(View.VISIBLE);
                    } else {
                        btn.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 初始化底部圆点指示器
     */
    private void initDots() {
        LinearLayout layout = findViewById(R.id.guide_l);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        params.setMargins(10, 0, 10, 0);
        dotViews = new ImageView[imgs.length];
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setImageResource(R.drawable.guide_selector);
            } else {
                imageView.setImageResource(R.drawable.guide_white);
            }
            dotViews[i] = imageView;
            final int finalI = i;
            dotViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vp.setCurrentItem(finalI);
                }
            });
            layout.addView(imageView);
        }
    }

    /**
     * 初始化图片
     */
    private void initImgs() {
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(imgs[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
    }


    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

//    public void transformPage(View page, float position) {
//        int pageWidth = page.getWidth();
//
//        if (position < -1) { // [-Infinity,-1)
//            // This page is way off-screen to the left.
//            page.setAlpha(0);
//
//        } else if (position <= 0) { // [-1,0]
//            // Use the default slide transition when moving to the left page
//            page.setAlpha(1);
//            page.setTranslationX(0);
//            page.setScaleX(1);
//            page.setScaleY(1);
//
//        } else if (position <= 1) { // (0,1]
//            // Fade the page out.
//            page.setAlpha(1 - position);
//
//            // Counteract the default slide transition
//            page.setTranslationX(pageWidth * -position);
//
//            // Scale the page down (between MIN_SCALE and 1)
//            float scaleFactor = MIN_SCALE
//                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
//            page.setScaleX(scaleFactor);
//            page.setScaleY(scaleFactor);
//
//        } else { // (1,+Infinity]
//            // This page is way off-screen to the right.
//            page.setAlpha(0);
//        }
//    }


        /**
         * 手风琴效果（水平方向缩放）
         */



//        @Override
//        public void transformPage( View page, float position) {
//            if (position < 0f) {
//                page.setPivotX(page.getWidth());
//                page.setScaleX(1f + position * 0.5f);
//            } else if (position < 1f) {
//                page.setPivotX(0f);
//                page.setScaleX(1f - position * 0.5f);
//            }
//        }

        private static final float DEF_MAX_ROTATE = 12.0f;
        private float mMaxRotate = DEF_MAX_ROTATE;



        @Override
        public void transformPage( View page, float position) {
            page.setPivotY( page.getHeight());
            if (position < -1f) {//[-Infinity, -1)
                page.setRotation(-mMaxRotate);
                page.setPivotX(page.getWidth());
            } else if (position <= 1f) {//[-1, 1]
                if (position < 0f) {//[-1, 0)
                    page.setRotation(mMaxRotate * position);
                    page.setPivotX(page.getWidth() * (0.5f - 0.5f * position));
                } else { //[0, 1]
                    page.setRotation(mMaxRotate * position);
                    page.setPivotX(page.getWidth() * (0.5f - 0.5f * position));
                }
            } else {//(1, +Infinity]
                page.setRotation(mMaxRotate);
                page.setPivotX(0f);
            }
        }
    }

}

