package com.henry.jetPackTest.ViewBindingTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.henry.basic.databinding.ActivityViewbindingBinding;
import com.henry.basic.databinding.InnerLayoutBinding;


public class ViewBindingActivity extends AppCompatActivity {
    //ViewBind背后不是用APT完成的，而是用Gradle插件完成的。
    ActivityViewbindingBinding binding;
    InnerLayoutBinding innerbing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//           setContentView(R.layout.activity_viewbinding);
        /**
         * 以前的用法
         */
//        TextView textView1 = findViewById(R.id.viewbing1);
//        TextView textView2 = findViewById(R.id.viewbing2);
//        TextView textView3 = findViewById(R.id.viewbing3);

        binding = ActivityViewbindingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.viewbing1.setText("ViewBind");

    }
}