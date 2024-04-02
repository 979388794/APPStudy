package com.henry.cmaketest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.henry.basic.R;

/**
 * @author: henry.xue
 * @date: 2024-04-01
 */
public class jniActivity extends AppCompatActivity {
    static {
        System.loadLibrary("myjni");
    }

    private int num = 1;
    String TAG = "henry";
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jnitest);
        JNIDemo demo = new JNIDemo();
        tv = findViewById(R.id.jniview);
        Log.d(TAG, "      : " + demo.stringJni());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
                test2();
            }
        });
    }

    public native void test();

    public native void test2();
}

