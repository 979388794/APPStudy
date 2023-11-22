package com.example.windowManagerTest.My_Floating_Window;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.Nullable;
import com.example.HomeScreen.R;

import java.util.ArrayList;
import java.util.List;

public class MainService extends Service {
    Button btnView;
    WindowManager windowManager;
    WindowManager.LayoutParams params;
    Boolean isAdded;
    ActivityManager mActivityManager;
    public static String OPERATION = "是否需要开启";
    public static int OPERATION_SHOW = 1;
    public static int OPERATION_HIDE = 2;

    int HANDLE_CHECK_ACTIVITY = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("xuejie","----onStartCommand------");
        int operation = intent.getIntExtra(OPERATION, 3);
        if (operation == OPERATION_SHOW) {
            mHandler.sendEmptyMessage(HANDLE_CHECK_ACTIVITY);
        } else if (operation == OPERATION_HIDE) {
            mHandler.removeMessages(HANDLE_CHECK_ACTIVITY);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        createWindowView();
        super.onCreate();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createWindowView() {
        btnView = new Button(getApplicationContext());
        btnView.setBackgroundResource(R.drawable.cluo);
        windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();

        // 设置Window Type
     //   params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 设置悬浮框不可触摸
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应
        params.format = PixelFormat.RGBA_8888;
        // 设置悬浮框的宽高
        params.width = 200;
        params.height = 200;
        params.gravity = Gravity.LEFT;
        params.x = 200;
        params.y = 000;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        // 设置悬浮框的Touch监听
        btnView.setOnTouchListener(new View.OnTouchListener() {
            //保存悬浮框最后位置的变量
            int lastX, lastY;
            int paramX, paramY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = params.x;
                        paramY = params.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        params.x = paramX + dx;
                        params.y = paramY + dy;
                        // 更新悬浮窗位置
                        windowManager.updateViewLayout(btnView, params);
                        break;
                }
                return true;
            }
        });
        windowManager.addView(btnView, params);
        isAdded = true;
    }


    /**
     * 判断当前界面是否是桌面
     *
     * android 6.0以上只能判断当前应用包名和Launcher
     */
    private boolean isAtHome() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = mActivityManager.getRunningTasks(1);
        Log.d("xuejie","----栈顶进程为---"+runningTaskInfos.get(0).topActivity.getPackageName());
        return getHomeApplicationList().contains(runningTaskInfos.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */

    /**
     * 获得属于桌面的应用的应用包名称
     * 返回包含所有包名的字符串列表数组
     * @return
     */
    private List<String> getHomeApplicationList() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo  resolveInfo : resolveInfos) {
            names.add(resolveInfo.activityInfo.packageName);
        }
        return names;
    }

    //定义一个更新界面的Handler
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
         if (msg.what==HANDLE_CHECK_ACTIVITY) {
                    if (isAtHome()) {
                     //   Log.d("xuejie","----在桌面------");
                        if (!isAdded) {
                            windowManager.addView(btnView, params);
                            isAdded = true;
                            new Thread(new Runnable() {
                                public void run() {
                                    for (int i = 0; i < 10; i++) {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        Message m = new Message();
                                        m.what = 2;
                                        mHandler.sendMessage(m);
                                    }
                                }
                            }).start();
                        }
                    } else {
                   //     Log.d("xuejie","----不在桌面------");
                        if (isAdded) {
                            windowManager.removeView(btnView);
                            isAdded = false;
                        }
                    }
                    mHandler.sendEmptyMessageDelayed(HANDLE_CHECK_ACTIVITY, 100);
            }
        }
    };

    @Override
    public void onDestroy() {
       if(isAdded){
           windowManager.removeView(btnView);
       }
        mHandler.removeCallbacksAndMessages(null);
        windowManager=null;
        mHandler=null;
        super.onDestroy();
    }
}
