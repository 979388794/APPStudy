package com.henry.windowManagerTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henry.windowManagerTest.FPS.FPSService;
import com.henry.windowManagerTest.floatwindow.WindowActivity;
import com.henry.basic.R;

public class windowManagerActivity extends AppCompatActivity implements View.OnClickListener {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_manager);

        textView=findViewById(R.id.strformat);
        String formatStr = getResources().getString(R.string.old);
        textView.setText(String.format(formatStr, "小五", 23, "家里蹲", "老牛公司", 9000.0));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                startService(new Intent(this, FPSService.class).putExtra(FPSService.FPS_COMMAND, FPSService.FPS_COMMAND_OPEN));
                break;

            case R.id.button6:
                startActivity(new Intent(this, WindowActivity.class));
                break;
        }
    }
}





