package com.henry.imageloadlibrary;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.henry.basic.R;
import com.henry.imageloadlibrary.GlideTest.ProgressInterceptor;
import com.henry.imageloadlibrary.GlideTest.ProgressListener;


public class ImageloadActivity extends AppCompatActivity {


    String url = "https://pic.netbian.com/uploads/allimg/240420/003439-17135444792cad.jpg";

    ImageView image;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageload);
        image = (ImageView) findViewById(R.id.image);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("加载中");
    }

    public void loadImage(View view) {

        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                progressDialog.setProgress(progress);
            }
        });
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(new GlideDrawableImageViewTarget(image) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        progressDialog.show();
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<?
                            super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressDialog.dismiss();
                        ProgressInterceptor.removeListener(url);
                    }
                });
    }
}