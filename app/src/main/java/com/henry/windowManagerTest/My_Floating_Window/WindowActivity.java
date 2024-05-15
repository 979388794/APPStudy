package com.henry.windowManagerTest.My_Floating_Window;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.henry.basic.R;

public class WindowActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_on;
    Button btn_off;
    Boolean isOpen = false;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        bindViews();
    }

    private void bindViews() {
        btn_on = findViewById(R.id.btn_on);
        btn_on.setOnClickListener(this);
        btn_off = findViewById(R.id.btn_off);
        btn_off.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_on:
                mIntent = new Intent(WindowActivity.this, FloatService.class);
                mIntent.putExtra(FloatService.OPERATION, FloatService.OPERATION_SHOW);
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivity(intent);
                } else {
                    startService(mIntent);
                    Toast.makeText(WindowActivity.this, "悬浮框已开启~", Toast.LENGTH_SHORT).show();
                    isOpen = true;
                }
                break;
            case R.id.btn_off:
                if (isOpen) {
                    stopService(mIntent);
                    isOpen = false;
                }
                Toast.makeText(WindowActivity.this, "悬浮框已关闭~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isOpen) {
            stopService(mIntent);
            isOpen = false;
        }
    }
}