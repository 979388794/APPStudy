package com.henry.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;


import android.os.Bundle;
import android.widget.TextView;

import com.henry.basic.R;

public class TimerActivity extends AppCompatActivity
{

    private TimerViewModel timerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_main);
        iniComponent();
    }

    private void iniComponent()
    {
        final TextView tvTime = findViewById(R.id.tvTime);
        //通过ViewModelProviders得到ViewModel，如果ViewModel不存在就创建一个新的，如果已经存在就直接返回已经存在的
         timerViewModel =new ViewModelProvider((ViewModelStoreOwner) this,
                 (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(TimerViewModel.class);

        timerViewModel.setOnTimeChangeListener(new TimerViewModel.OnTimeChangeListener()
        {
            @Override
            public void onTimeChanged(final int second)
            {
                //更新UI界面
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        tvTime.setText("TIME:" + second);
                    }
                });
            }
        });

        timerViewModel.startTiming();
    }
}