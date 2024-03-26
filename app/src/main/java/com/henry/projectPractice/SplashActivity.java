package com.henry.projectPractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henry.basic.R;

/**
 * @author: henry.xue
 * @date: 2024-03-26
 */

public class SplashActivity extends AppCompatActivity {

    private LinearLayout ll;
    private TextView mCountDownTextView;
    private MyCountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ll = findViewById(R.id.main_ll);
        mCountDownTextView = (TextView) findViewById(R.id.start_skip_count_down);
        //设置渐变效果
        setAlphaAnimation();
        countdown();
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
//                jumpActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void countdown() {
        mCountDownTimer = new MyCountDownTimer(4000, 1000);
        mCountDownTextView.setText("4s 跳过");
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
        startActivity(intent);
        finish();
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
            mCountDownTextView.setText("0s 跳过");
        }

        public void onTick(long millisUntilFinished) {
            mCountDownTextView.setText(millisUntilFinished / 1000 + "s 跳过");
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

