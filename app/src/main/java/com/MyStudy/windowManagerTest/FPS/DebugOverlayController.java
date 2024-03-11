package com.MyStudy.windowManagerTest.FPS;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class DebugOverlayController {
    private final WindowManager mWindowManager;
    private final Context mReactContext;
    private FrameLayout mFPSDebugViewContainer;

    public DebugOverlayController(Context reactContext) {
        mReactContext = reactContext;
        mWindowManager = (WindowManager) reactContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public void setFpsDebugViewVisible() {
        if (mFPSDebugViewContainer != null) {
            stopFps();
        }
        mFPSDebugViewContainer = new FpsView(mReactContext);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.x = 0;
        params.y = 0;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        mWindowManager.addView(mFPSDebugViewContainer, params);
    }

    public void stopFps() {
        if(null==mFPSDebugViewContainer){
            return;
        }
        mFPSDebugViewContainer.removeAllViews();
        mWindowManager.removeView(mFPSDebugViewContainer);
        mFPSDebugViewContainer = null;
    }
}
