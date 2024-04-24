package com.henry.imageloadlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.henry.basic.MainActivity;
import com.henry.basic.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;


public class ImageloadActivity extends AppCompatActivity {


    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageload);
        imageView = findViewById(R.id.im1);
        imageView2 = findViewById(R.id.im2);
        imageView3 = findViewById(R.id.im3);
        String uri = "https://pic.netbian.com/uploads/allimg/240420/003439-17135444792cad.jpg";

        String uri2 = "https://pic.netbian.com/uploads/allimg/240416/002020-1713198020e46e.jpg";
        Picasso.get().load(uri)
                .placeholder(R.drawable.apple)
                .error(R.drawable.litchi)
                .priority(Picasso.Priority.HIGH)
                // .priority(Picasso.Priority.LOW)
                .into(imageView);
        Picasso.get().load(R.drawable.apple).into(imageView2);
        Picasso.get().load(uri2).into(imageView3);

        Picasso.get()
                .setIndicatorsEnabled(true);//显示指示器
        Picasso.get()
                .setLoggingEnabled(true);//开启日志打印


//        Picasso.get().load(uri).resize(dp2px(this, 200f), dp2px(this, 200f)).into(imageView);
//        Picasso.get().load(uri).placeholder(R.drawable.kenan).error(R.drawable.bairen).into(imageView);
    }

    private int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * Created by zhouwei on 17/2/26.
     */

    public class CustomDownloader implements Downloader {


        @NonNull
        @Override
        public Response load(@NonNull Request request) throws IOException {
            return null;
        }

        @Override
        public void shutdown() {

        }


    }
}