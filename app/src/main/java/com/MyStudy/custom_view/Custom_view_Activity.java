package com.MyStudy.custom_view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.MyStudy.Basic_control_view.R;

public class Custom_view_Activity extends AppCompatActivity {
    private Button bttest;
    private MyTextview mtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        bttest = findViewById(R.id.bt_test);
        mtv = findViewById(R.id.view_test);
        bttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Custom_view_Activity.this, ViewTest_Activity.class);
                startActivity(intent);
             //   mtv.setRotationY(45);
            }
        });
    }

}

