package com.MyStudy.PowerControlTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.MyStudy.Basic_control_view.R;

public class PowerContronlActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_contronl2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Intent intent = new Intent("xuejie");
        intent.putExtra("msg", "学习WAKE_LOCK。。。");
        sendBroadcast(intent);
    }

}