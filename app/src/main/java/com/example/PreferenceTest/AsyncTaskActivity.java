package com.example.PreferenceTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.HomeScreen.R;

public class AsyncTaskActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tv;
    private Button b9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        progressBar = findViewById(R.id.progressBar);
        tv = findViewById(R.id.tv_num);
        b9 = findViewById(R.id.b9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsyncTask myAsyncTask = new MyAsyncTask(tv, progressBar);
                myAsyncTask.execute(100);
            }
        });
    }



}