package com.example.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.example.HomeScreen.R;

public class LiveDataActivity extends AppCompatActivity {

        private static final String TAG = "LiveDataDemo";
        private final MutableLiveData<Long> liveData = new MutableLiveData<>();
        private TextView textView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_live_data);

            textView = findViewById(R.id.textView);

            // TODO：为了方便演示，我们直接在MainActivity中操作LiveData，实际项目中不要这样编写代码，应该把LiveData放到ViewModel中
            liveData.observe(this, new Observer<Long>() {
                @Override
                public void onChanged(Long aLong) {
                    textView.setText(String.valueOf(aLong / 1000));
                }
            });

            countDown();
        }

        public void countDown() {
            new CountDownTimer(1 * 60 * 1000, 1 * 1000) {
                @Override
                public void onTick(long l) {
                    Log.i(TAG, "onTick: " + l);
                    // TODO：为了方便演示，我们直接在MainActivity中操作LiveData，实际项目中不要这样编写代码，应该把LiveData放到ViewModel中
                    liveData.setValue(l);
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
    }



