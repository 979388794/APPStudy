package com.henry.jetPackTest.viewModelTest;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.henry.basic.databinding.ActivityViewbindingBinding;
import com.henry.basic.databinding.ActivityViewmodelBinding;


/**
 * @author: henry.xue
 * @date: 2024-03-24
 */


public class ViewModelActivity extends AppCompatActivity {
    ActivityViewmodelBinding binding;
    private MyViewModel myViewModel;

    private int mynumber = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewmodelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(MyViewModel.class);

//        NoViewModel();
        UseViewModel();
    }

    public void NoViewModel() {
        binding.viewmodeltext.setText(String.valueOf(mynumber));
        binding.viewmodel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewmodeltext.setText(String.valueOf(++mynumber));
            }
        });
    }

    public void UseViewModel() {

        binding.viewmodeltext.setText(String.valueOf(myViewModel.number));
        binding.viewmodel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewmodeltext.setText(String.valueOf(++myViewModel.number));
            }
        });
    }

}
















