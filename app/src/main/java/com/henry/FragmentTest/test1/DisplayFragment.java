package com.henry.FragmentTest.test1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.henry.basic.R;

/**
 * @author: henry.xue
 * @date: 2024-05-15
 */
public class DisplayFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_fragment, container, false);
        return view;
    }
}