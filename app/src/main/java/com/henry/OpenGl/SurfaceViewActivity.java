package com.henry.OpenGl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.henry.basic.R;

public class SurfaceViewActivity extends AppCompatActivity {

    GlsurfaceView glsurfaceView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) inflater.inflate(R.layout.activity_surfaceview, null);
        setContentView(linearLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        glsurfaceView = new GlsurfaceView(this);
        linearLayout.addView(glsurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        // myThreadGpu.interrupt();
        super.onStop();
        linearLayout.removeView(glsurfaceView);
    }

}
