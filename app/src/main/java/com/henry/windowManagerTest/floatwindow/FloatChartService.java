package com.henry.windowManagerTest.floatwindow;

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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.henry.basic.R;


/**
 * @author: henry.xue
 * @date: 2024-05-15
 */
public class FloatChartService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    ImageView iv_zoom_btn;

    View floatView;
    WindowManager windowManager;
    WindowManager.LayoutParams params;
    Boolean isAdded;
    public static String OPERATION = "是否需要开启";
    public static int OPERATION_SHOW = 1;
    public static int OPERATION_HIDE = 2;
    int HANDLE_CHECK_ACTIVITY = 0;
    boolean isDragged = false;//是否正在拖拽中
    boolean isEdit = false;//是否进入编辑状态

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        floatView = inflater.inflate(R.layout.float_view_layout, null);
        iv_zoom_btn = (floatView).findViewById(R.id.iv_zoom_btn);
        windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置悬浮框的宽高
        params.width = 700;
        params.height = 350;
        ;
//        params.gravity = Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        // 设置悬浮框的宽高
        // 设置Window Type
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        // 设置悬浮框的Touch监听
        floatView.setOnTouchListener(new View.OnTouchListener() {
            //保存悬浮框最后位置的变量
            int lastX, lastY;
            int paramX, paramY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        iv_zoom_btn.setVisibility(View.VISIBLE);
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
                        windowManager.updateViewLayout(floatView, params);
                        break;
                    case MotionEvent.ACTION_UP:
                        iv_zoom_btn.setVisibility(View.GONE);
                        break;
                }

                return true;
            }
        });
        windowManager.addView(floatView, params);
        isAdded = true;
    }


    //更新界面的Handler
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLE_CHECK_ACTIVITY) {
                if (!isAdded) {
                    windowManager.addView(floatView, params);
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
                mHandler.sendEmptyMessageDelayed(HANDLE_CHECK_ACTIVITY, 100);
            }
        }
    };

    @Override
    public void onDestroy() {
        if (isAdded) {
            windowManager.removeView(floatView);
        }
        mHandler.removeCallbacksAndMessages(null);
        windowManager = null;
        mHandler = null;
        super.onDestroy();
    }
}
