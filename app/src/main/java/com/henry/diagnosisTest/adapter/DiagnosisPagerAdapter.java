package com.henry.diagnosisTest.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.henry.diagnosisTest.fragment.DiagnosisFragment;

import java.util.List;


public class DiagnosisPagerAdapter extends FragmentPagerAdapter {

    List<DiagnosisFragment> diagnosisInfoLists;

    public DiagnosisPagerAdapter(@NonNull FragmentManager fm, int behavior, List<DiagnosisFragment> diagnosisInfoLists) {
        super(fm, behavior);
        this.diagnosisInfoLists = diagnosisInfoLists;
    }

    @Override
    public int getCount() {
        return diagnosisInfoLists != null ? diagnosisInfoLists.size() : 0;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return diagnosisInfoLists.get(position);
    }

}