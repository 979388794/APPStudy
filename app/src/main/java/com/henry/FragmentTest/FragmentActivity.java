package com.henry.FragmentTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.henry.FragmentTest.NavigationBar_Message.Navigation_Message_Activity;
import com.henry.FragmentTest.NavigationBar.Navigation_bar_Activity;
import com.henry.basic.R;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {
    Button B1;
    Button B2;
    Button B3;
    Button B4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2);
        B1 = findViewById(R.id.BUTT1);
        B2 = findViewById(R.id.BUTT2);
        B3 = findViewById(R.id.BUTT3);
        B4 = findViewById(R.id.BUTT4);
        B1.setOnClickListener(this);
        B2.setOnClickListener(this);
        B3.setOnClickListener(this);
        B4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.BUTT1:
                intent.setClass(FragmentActivity.this, FragmentTest1.class);
                break;
            case R.id.BUTT2:
                intent.setClass(FragmentActivity.this, FragmentTest2.class);
                break;
            case R.id.BUTT3:
                intent.setClass(FragmentActivity.this, Navigation_bar_Activity.class);
                break;
            case R.id.BUTT4:
                intent.setClass(FragmentActivity.this, Navigation_Message_Activity.class);
                break;

            default:
                break;
        }
        startActivity(intent);
    }
}