package com.example.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageviewActivity extends AppCompatActivity {
private ImageView im3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
//        im3=findViewById(R.id.im3);
//        Glide.with(this).load("https://pic4.zhimg.com/80/v2-6978bd3bb1f7e8bd6927ddde8dc58ac3_720w.jpg")
//                .into(im3);
    }
}