package com.example.HomeScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.xuejie.XuejieTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestActivity extends AppCompatActivity {
    private int minute = 2;//这是分钟
    private int second = 0;//这是分钟后面的秒数。这里是以30分钟为例的，所以，minute是30，second是0
    private TextView timeView;
    TextView Cpurate;
    private MyHandler handler;
    DecimalFormat df = new DecimalFormat("#0.00");

    private Timer timer;
    Double cpu;
    private MyTimerTask timerTask;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        timeView = findViewById(R.id.target);
        Cpurate = findViewById(R.id.CpuRate);
        handler = new MyHandler();
        // startTimeCount();


        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    cpu = Double.parseDouble(df.format(Double.parseDouble(getCPURate()) * 100));
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Cpurate.setText("cpu占用率为" + String.valueOf(cpu) + "%");
                }
            }
        }.start();
    }


    /**
     * ^ 表示匹配行的开头。
     * cpu 匹配 "cpu" 这个单词。
     * \\s+ 匹配一个或多个空格字符。
     * (\\d+\\s+){9} 匹配由一个或多个数字加上一个或多个空格字符组成的序列，重复9次。
     * \\d+ 匹配一个或多个数字。
     * $ 匹配行的结尾。 因此，整个正则表达式可以匹配以 "cpu" 开头，后面跟着10个由空格分隔的数字的行。
     */

    public static String getCPURate() {
        String path = "/proc/stat";// 系统CPU信息文件
        long Totaljiffies[] = new long[2];
        long totalIdle[] = new long[2];
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        Pattern pattern = Pattern.compile("^cpu\\s+(\\d+\\s+){9}\\d+$", Pattern.MULTILINE);
        //正则表达式，只获取第一行

        for (int i = 0; i < 2; i++) {  //每一次调用分为两次获取 方便求差
            Totaljiffies[i] = 0;
            totalIdle[i] = 0;
            try {
                fileReader = new FileReader(path);
                bufferedReader = new BufferedReader(fileReader, 8192);
                String str;
                while ((str = bufferedReader.readLine()) != null) {  //读取stat信息
                    if (str.toLowerCase().startsWith("cpu")) {//以cpu开头的
                        Matcher matcher = pattern.matcher(str);//直接获取第一行cpu开头的数据
                        // 不需要cpu0-7的，那样的话还得多几步运算
                        while (matcher.find()) {
                            String[] values = extractValues(matcher.group());
                            Totaljiffies[i] = sumValues(values);
                            totalIdle[i] = Long.parseLong(values[3]);
                        }
                    }
                    if (i == 0) {//第一次获取后进行延时等待系统更新信息
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        double rate = 0;
        if (Totaljiffies[1] > Totaljiffies[0]) {//正常情况下第二次总的jiffies一定比第一次获得的数据大
            rate = 1.0 * ((Totaljiffies[1] - totalIdle[1]) - (Totaljiffies[0] - totalIdle[0]))
                    / (Totaljiffies[1] - Totaljiffies[0]);
        }
        return String.valueOf(rate);
    }


    /**
     * 头行去掉cpu，合并成String[]数组
     *
     * @param input
     * @return
     */
    public static String[] extractValues(String input) {
        String[] parts = input.split("\\s+");
        String[] values = new String[parts.length - 1]; // 去掉 "cpu"，所以长度减一
        System.arraycopy(parts, 1, values, 0, parts.length - 1);
        return values;
    }

    /**
     * 求数组和
     *
     * @param input
     * @return
     */
    public static Long sumValues(String input[]) {
        Long sum = Long.valueOf(0);
        for (String value : input) {
            sum += Integer.parseInt(value);
        }
        return sum;
    }


    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                //主线程执行操作
                //这是接收回来处理的消息
                if (minute == 0) {
                    if (second == 0) {
                        //都为0时做赋空操作
                        timeView.setText("Time end!");
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                        if (timerTask != null) {
                            timerTask = null;
                        }
                    } else {
                        second--;
                        if (second >= 10) {
                            timeView.setText("0" + minute + ":" + second);
                        } else {
                            timeView.setText("0" + minute + ":0" + second);
                        }
                    }
                } else {
                    if (second == 0) {
                        second = 59;
                        minute--;
                        if (minute >= 10) {
                            timeView.setText(minute + ":" + second);
                        } else {
                            timeView.setText("0" + minute + ":" + second);
                        }
                    } else {
                        second--;
                        if (second >= 10) {
                            if (minute >= 10) {
                                timeView.setText(minute + ":" + second);
                            } else {
                                timeView.setText("0" + minute + ":" + second);
                            }
                        } else {
                            if (minute >= 10) {
                                timeView.setText(minute + ":0" + second);
                            } else {
                                timeView.setText("0" + minute + ":0" + second);
                            }
                        }
                    }

                }
            }
        }
    }

    private void startTimeCount() {
        //防止多次点击开启计时器
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        timer = new Timer(true);
        timerTask = new MyTimerTask();
        timer.schedule(timerTask, 0, 1000);
    }


}