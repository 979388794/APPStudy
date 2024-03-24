package com.henry.jetPackTest.LiveDataTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.henry.basic.R;
import com.henry.jetPackTest.LiveDataTest.JumpTest.LiveDataSecondActivity;
import com.henry.jetPackTest.LiveDataTest.weixindemo.MyService;

public class LiveDataActivity extends AppCompatActivity {
    String TAG = "Henry";
    TextView data;
    Button button;
    Button jump;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data2);
        data = findViewById(R.id.LiveData2);
        button = findViewById(R.id.livedata_Button);
        jump = findViewById(R.id.livedata_jump);
        //test1();
        test3();
    }

    public void test3() {
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //旧数据
                myLiveData.getInfo1().setValue("this message is from LiveDataActivity");
                startActivity(new Intent(LiveDataActivity.this,
                        LiveDataSecondActivity.class));
            }
        });
    }


    public void test2() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(LiveDataActivity.this, MyService.class));
                Toast.makeText(LiveDataActivity.this, "启动服务成功",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //观察者，界面可见的情况下才会下列事情
        myLiveData.getInfo1().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "界面可见，当前用户正在查看微信列表界面，更新消息列表UI界面" + s);
                Toast.makeText(LiveDataActivity.this, "更新UI界面成功" + s, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void test1() {

        //1.观察者 眼睛 环节
        myLiveData.getInfo1().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                data.setText(s); // 更新 UI
            }
        });

        //2. 触发数据改变环节
        myLiveData.getInfo1().setValue("--------default------------");//主线程修改数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    myLiveData.getInfo1().postValue("--------3秒后修改UI------------");
                    Thread.sleep(6000);
                    myLiveData.getInfo1().postValue("--------6秒后修改UI------------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}