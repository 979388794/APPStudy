package com.henry.jetPackTest.dataBindingTest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.henry.basic.R;
import com.henry.basic.databinding.DatabindingtestBinding;


/**
 * @author: henry.xue
 * @date: 2024-03-22
 */
public class DataBindingTestActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabindingtestBinding binding = DataBindingUtil.setContentView(this, R.layout.databindingtest);
        user = new User("name", "password");
        binding.setUser(user);//必须要建立联系，否则没有任何效果
        //Model---->View
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {

                    try {
                        Thread.sleep(1000);
                        user.setName(user.getName() + "" + i);
                        Log.d("Henry","   "+user.getName());
                        user.setPassword(user.getPassword() + "" + i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();
    }
}