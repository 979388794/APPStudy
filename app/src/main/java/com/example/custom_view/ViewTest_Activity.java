package com.example.custom_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.HomeScreen.R;

public class ViewTest_Activity extends AppCompatActivity {
    private myview t1;
    private Button b1;
    private Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        t1=findViewById(R.id.t1);
        b1=findViewById(R.id.b1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void run(View view) {
        t1.scrollBy(-10,-10);
    }
    public void run2(View view) {

        t1.setTranslationX(100);
        t1.setTranslationY(200);
    }
}
