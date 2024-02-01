package com.example.OpenGl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.HomeScreen.R;
import com.example.OpenGl.GlsurfaceView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SurfaceViewActivity extends AppCompatActivity {
    int gpuRate;
    ThreadGpu myThreadGpu;
    TextView t1;
    double set_gpu_rate; //GPU占用率设置值  默认百分之80
    GlsurfaceView glsurfaceView;
    LinearLayout linearLayout;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) inflater.inflate(R.layout.activity_surfaceview, null);
        setContentView(linearLayout);
        set_gpu_rate = getIntent().getDoubleExtra("gpu_set", 80);//默认占比80
        t1 = findViewById(R.id.test_view);
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        gpuRate = getGpuCurFreq();
                        t1.setText("GPU当前占用率为" + gpuRate + "%");
                        break;
                }
            }
        };
        myThreadGpu = new ThreadGpu();
        myThreadGpu.start();
    }

    /**
     * 获取GPU当前工作占用率
     * 兼容高通与展锐，高通qcom , 展锐 sl8541e
     *
     * @return
     */
    public static int getGpuCurFreq() {
        String gpuCurFreq = "null";
        Boolean QualcommorUnisoc = false;
        int a = 0;
        BufferedReader reader = null;
        try {
            if (Build.HARDWARE.matches("qcom")) {
                reader = new BufferedReader(new FileReader("/sys/class/kgsl/kgsl-3d0/gpu_busy_percentage"));
                gpuCurFreq = reader.readLine();
                QualcommorUnisoc = true;
                a = Integer.valueOf(gpuCurFreq.substring(0, gpuCurFreq.length() - 2)).intValue();
            } else if (Build.HARDWARE.contains("uis7870")) {
                reader = new BufferedReader(new FileReader("/sys/kernel/debug/mali0/gpu_utilisation"));
                gpuCurFreq = reader.readLine();
                QualcommorUnisoc = false;
                a = Integer.parseInt(gpuCurFreq.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return a;
    }

    /**
     * 获取高通平台GPU实时占用率
     */
//    class ThreadGpu extends Thread {
//        @Override
//        public void run() {
//            while (!isInterrupted()) {
//                gpuRate = getGpuCurFreq();
//                t1.setText("GPU当前占用率为" + gpuRate + "%");
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    break;
//                }
//            }
//        }
//    }


    class ThreadGpu extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                Message message = new Message();
                message.what = 1;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                handler.sendMessage(message);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        glsurfaceView = new GlsurfaceView(this, (int) set_gpu_rate);
        linearLayout.addView(glsurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
//        myThreadGpu = new ThreadGpu();
//        myThreadGpu.start();
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
