package com.henry.jetPackTest.navigationTest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.henry.basic.R;

/**
 * @author: henry.xue
 * @date: 2024-03-26
 */
public class Page2Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.page2fragment, container, false);
    }

    //点击事件，前进和后退点击事件注册
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnforward = view.findViewById(R.id.Page2forward);
        btnforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_page3);
//                Navigation.findNavController(view).navigateUp();
            }
        });

        Button btnbackward = view.findViewById(R.id.Page2backward);
        btnbackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_page1);
            }
        });

    }
}
