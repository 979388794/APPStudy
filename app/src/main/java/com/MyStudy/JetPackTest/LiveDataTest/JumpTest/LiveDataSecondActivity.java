package com.MyStudy.JetPackTest.LiveDataTest.JumpTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.Toast;

import com.MyStudy.Basic_control_view.R;
import com.MyStudy.JetPackTest.LiveDataTest.myLiveData;

public class LiveDataSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data_second);
        //后观察数据 可以收到前面的数据
        myLiveData.getInfo1().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LiveDataSecondActivity.this, "观察数据变化 消息为=    " + s,
                        Toast.LENGTH_SHORT).show();
            }
        });
        //现在的新数据
        myLiveData.getInfo1().postValue("new Value");
    }

}

