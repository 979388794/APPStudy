package com.henry.DisplayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.henry.basic.R;

public class DisplayMainActivity extends AppCompatActivity {
    public TextView t;

    private void setCustomDensity(Activity activity, final Application application) {
        final DisplayMetrics appDisplayMetrics = activity.getResources().getDisplayMetrics();
        t.setText(
                "屏幕密度" + appDisplayMetrics.densityDpi + "\n" +
                        "屏幕密度比" + appDisplayMetrics.density + " \n" +
                        "屏幕高度" + appDisplayMetrics.heightPixels + " \n" +
                        "屏幕宽度" + appDisplayMetrics.widthPixels + " \n" +
                        "屏幕密度" + appDisplayMetrics.scaledDensity + " \n" +
                        "屏幕x轴像素密度" + appDisplayMetrics.xdpi + " \n" +
                        "屏幕y轴像素密度" + appDisplayMetrics.ydpi + " \n"
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_main);
        Display display = getWindowManager().getDefaultDisplay();
//从Display获取DisplayMetrics信息
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        t = findViewById(R.id.my_text);
        setCustomDensity(this, this.getApplication());
    }


}