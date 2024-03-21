package com.MyStudy.JetPackTest.OkLiveDataBusTest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.MyStudy.Basic_control_view.R;

public class OkLiveDataBusActivity extends AppCompatActivity {

    Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_live_data_bus);
        button = findViewById(R.id.OKlivedata_jump);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OkLiveDataBusActivity.this,
                        OkLiveDataBusSecondActivity.class));
            }
        });

        OkLiveDataBusJava.with("data", String.class, true).postValue("old 数据-----------");

    }

}