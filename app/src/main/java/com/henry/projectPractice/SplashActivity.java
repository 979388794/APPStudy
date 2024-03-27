package com.henry.projectPractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.henry.basic.databinding.ActivitySplashBinding;

/**
 * @author: henry.xue
 * @date: 2024-03-26
 */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySplashBinding binding;
    private MyCountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.startSkipCountDown.setOnClickListener(this::onClick);
        //设置渐变效果
        setAlphaAnimation();
        countdown();
        Log.i("henry", "onTick - " + Thread.currentThread() + ", id - " + Thread.currentThread().getId());
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
        binding.mainLl.setAnimation(animation);
        //设置动画监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                jumpActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
    }


    private void countdown() {
        mCountDownTimer = new MyCountDownTimer(4000, 1000);
        mCountDownTimer.start();
    }

    /**
     * 根据首次启动应用与否跳转到相应界面
     */
    private void jumpActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String First = sharedPreferences.getString("isFirst", "0");
        Intent intent = new Intent();
//        if ("0".equals(First)) {
//            intent.setClass(this, GuideActivity.class);
//        }else{
//            intent.setClass(this, MainActivity.class);
//        }
//        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        jumpActivity();
    }


    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以「 毫秒 」为单位倒计时的总数
         *                          例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
         *                          例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        public void onFinish() {
//            binding.startSkipCountDown.setText("0s 跳过");
            jumpActivity();
        }

        public void onTick(long millisUntilFinished) {
            binding.startSkipCountDown.setText(millisUntilFinished / 1000 + "s 跳过");
            Log.i("henry", "onTick - " + Thread.currentThread() + ", id - " + Thread.currentThread().getId());
        }

    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}

