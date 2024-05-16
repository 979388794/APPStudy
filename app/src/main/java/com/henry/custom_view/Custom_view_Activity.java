package com.henry.custom_view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.henry.basic.R;

public class Custom_view_Activity extends AppCompatActivity {
    private Button button;
    private MyTextview mtv;

    String TAG = "henry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        button = findViewById(R.id.bt_test);
        mtv = findViewById(R.id.view_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Custom_view_Activity.this, ViewTest_Activity.class);
//                startActivity(intent);
                //   mtv.setRotationY(45);
                Log.d(TAG, "------onclick-----");
            }
        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "-----onTouch------"+event.getAction());
                return false;
            }
        });


    }

}

