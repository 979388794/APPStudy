package com.henry.windowManagerTest.FPS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.henry.basic.R;

import java.util.Locale;

public class FpsView extends FrameLayout {
    private static final int UPDATE_INTERVAL_MS = 500;
    private final TextView mTextView;
    private final FpsDebugFrameCallback mFrameCallback;
    private final FPSMonitorRunnable mFPSMonitorRunnable;
    private final WindowManager mWindowManager;
    Point preP, curP;

    private float mOriginalX;
    private float mOriginalY;
    private float mOriginalRawX;
    private float mOriginalRawY;
    public FpsView(@NonNull Context reactContext) {
        super(reactContext);
        inflate(reactContext, R.layout.fps_view, this);
        findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startService(
                        new Intent(getContext(), FPSService.class).putExtra(FPSService.FPS_COMMAND, FPSService.FPS_COMMAND_CLOSE));
            }
        });
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mTextView = (TextView) findViewById(R.id.fps_text);
        mFrameCallback = new FpsDebugFrameCallback(ChoreographerCompat.getInstance(), reactContext);
        mFPSMonitorRunnable = new FPSMonitorRunnable();
        setCurrentFPS(0, 0, 0, 0);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFrameCallback.reset();
        mFrameCallback.start();
        mFPSMonitorRunnable.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mFrameCallback.stop();
        mFPSMonitorRunnable.stop();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.getLayoutParams();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preP = new Point((int) event.getRawX(), (int) event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                curP = new Point((int) event.getRawX(), (int) event.getRawY());
                int dx = curP.x - preP.x;
                int dy = curP.y - preP.y;
             //   if (Math.abs(dx) > 100 || Math.abs(dy) > 100) {
                    layoutParams.x += dx;
                    layoutParams.y += dy;
                    mWindowManager.updateViewLayout(this, layoutParams);

                    preP = curP;
               // }
                break;
        }

        return false;
    }

    private void setCurrentFPS(double currentFPS, double currentJSFPS, int droppedUIFrames, int total4PlusFrameStutters) {
        String fpsString = String.format(
                Locale.US,
                "UI: %.1f fps\n%d dropped so far\n%d stutters (4+) so far\nJS: %.1f fps",
                currentFPS,
                droppedUIFrames,
                total4PlusFrameStutters,
                currentJSFPS);
        mTextView.setText(fpsString);
        Log.d("debugtool", fpsString);
    }

    /**
     * Timer that runs every UPDATE_INTERVAL_MS ms and updates the currently displayed FPS.
     */
    private class FPSMonitorRunnable implements Runnable {

        private boolean mShouldStop = false;
        private int mTotalFramesDropped = 0;
        private int mTotal4PlusFrameStutters = 0;

        @Override
        public void run() {
            if (mShouldStop) {
                return;
            }
            mTotalFramesDropped += mFrameCallback.getExpectedNumFrames() - mFrameCallback.getNumFrames();
            mTotal4PlusFrameStutters += mFrameCallback.get4PlusFrameStutters();
            setCurrentFPS(mFrameCallback.getFPS(), mFrameCallback.getJSFPS(), mTotalFramesDropped, mTotal4PlusFrameStutters);
            mFrameCallback.reset();

            postDelayed(this, UPDATE_INTERVAL_MS);
        }

        public void start() {
            mShouldStop = false;
            post(this);
        }

        public void stop() {
            mShouldStop = true;
        }
    }


}


