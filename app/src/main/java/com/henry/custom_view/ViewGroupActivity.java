package com.henry.custom_view;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.henry.basic.R;

/**
 * @author: henry.xue
 * @date: 2024-05-16
 */
public class ViewGroupActivity extends AppCompatActivity {

    MyLayout myLayout;

    Button button1;

    Button button2;

    String TAG = "henry";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_test);

        myLayout = findViewById(R.id.my_layout);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "myLayout on touch---------");
                return false;
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "You clicked button1---------");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "You clicked button2----------");
            }
        });
    }


}
