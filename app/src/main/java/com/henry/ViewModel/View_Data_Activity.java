package com.henry.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.henry.basic.R;


public class View_Data_Activity extends AppCompatActivity {
    private static final String TAG = "LiveDataDemo";

    private TextView textView;
    private View_Data_Model viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        textView = findViewById(R.id.textView);
        viewModel = new ViewModelProvider(this).get(View_Data_Model.class);
        viewModel.getLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                textView.setText(String.valueOf(aLong / 1000));
            }
        });

        viewModel.getLiveDataMerger().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });

        //viewModel.mergeTest();
        viewModel.countDown();
    }
}