package com.MyStudy.JetPackTest.OkLiveDataBusTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.Toast;

import com.MyStudy.Basic_control_view.R;

public class OkLiveDataBusSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_live_data_bus_second);
        OkLiveDataBusJava.with("data", String.class, true).observe(this,
                new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(OkLiveDataBusSecondActivity.this,
                                "获取数据" + s, Toast.LENGTH_SHORT).show();
                    }
                });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                OkLiveDataBusJava.with("data", String.class, true).postValue("new 数据-----------");
            }
        }).start();
    }
}