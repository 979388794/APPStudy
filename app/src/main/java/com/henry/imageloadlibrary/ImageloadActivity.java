package com.henry.imageloadlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.henry.basic.R;
import com.squareup.picasso.Picasso;


public class ImageloadActivity extends AppCompatActivity {


    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageload);
        imageView = findViewById(R.id.im1);
        String uri = "https://pic.netbian.com/uploads/allimg/240424/003411-171389005188d6.jpg";
        Picasso.get().load(uri).into(imageView);
//        Picasso.get().load(uri).resize(dp2px(this, 200f), dp2px(this, 200f)).into(imageView);
//        Picasso.get().load(uri).placeholder(R.drawable.kenan).error(R.drawable.bairen).into(imageView);
    }

    private int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}