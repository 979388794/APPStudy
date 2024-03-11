package com.MyStudy.HotFixTest;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import java.io.File;
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //执行热修复。 插入补丁dex
        // /data/data/xxx/files/xxxx.dex
        // /sdcard/xxx.dex
         Hotfix.installPatch(this,new File("/sdcard/patch.dex"));





          registerComponentCallbacks(new ComponentCallbacks2() {
              @Override
              public void onTrimMemory(int level) {

              }

              @Override
              public void onConfigurationChanged(@NonNull Configuration newConfig) {

              }

              @Override
              public void onLowMemory() {

              }
          });
    }






}
