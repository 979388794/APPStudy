package com.MyStudy.PreferenceTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.MyStudy.Basic_control_view.R;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingFragment()).commit();
    }
}