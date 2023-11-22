package com.example.FragmentTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Display;

import com.example.HomeScreen.R;

public class FragmentTest2 extends AppCompatActivity {

    FragmentTransaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test2);
        Display dis = getWindowManager().getDefaultDisplay();
        mTransaction = getSupportFragmentManager().beginTransaction();
        if (dis.getWidth() > dis.getHeight()) {
            Fragment f1 = new Fragmentone();
            mTransaction.replace(R.id.LinearLayout1, f1).commit();
        } else {
            Fragment f2 = new FragmentTwo();
            mTransaction.replace(R.id.LinearLayout1, f2).commit();
        }

    }
}