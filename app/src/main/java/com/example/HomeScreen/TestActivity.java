package com.example.HomeScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {
    TextView view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        view1 = findViewById(R.id.target);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        double widthInches = widthPixels / (double) displayMetrics.xdpi;
        double heightInches = heightPixels / (double) displayMetrics.ydpi;
        double screenInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));
        String fromDouble = "" + screenInches;
        view1.setText(fromDouble);
    }









}