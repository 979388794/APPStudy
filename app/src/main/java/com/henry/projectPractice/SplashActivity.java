package com.henry.projectPractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.henry.basic.R;

/**
 * @author: henry.xue
 * @date: 2024-03-26
 */

public class SplashActivity extends AppCompatActivity {

    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ll = findViewById(R.id.main_ll);
        //设置渐变效果
        setAlphaAnimation();
    }

    /**
     * 设置渐变效果
     */
    private void setAlphaAnimation() {
        //生成动画对象
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        //设置持续时间3s
        animation.setDuration(3000);
        //给控件设置动画
        ll.setAnimation(animation);
        //设置动画监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                jump2Activity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 根据首次启动应用与否跳转到相应界面
     */
    private void jump2Activity() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String First = sharedPreferences.getString("isFirst", "0");
        Intent intent = new Intent();
//        if ("0".equals(First)) {
//            intent.setClass(this, GuideActivity.class);
//        }else{
//            intent.setClass(this, MainActivity.class);
//        }
        startActivity(intent);
        finish();
    }

}

