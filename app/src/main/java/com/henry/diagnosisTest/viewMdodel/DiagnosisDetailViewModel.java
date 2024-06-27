package com.henry.diagnosisTest.viewMdodel;


import androidx.lifecycle.MutableLiveData;


import com.henry.diagnosisTest.base.BaseViewModel;
import com.henry.diagnosisTest.inter.DiagnosisDetailNav;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;

import java.util.ArrayList;


public class DiagnosisDetailViewModel extends BaseViewModel<DiagnosisDetailNav> {
    public MutableLiveData<ArrayList<DiagnosisInfoList>> listMutableLiveData = new MutableLiveData<ArrayList<DiagnosisInfoList>>();
    public MutableLiveData<DiagnosisModule> diagnosisModule = new MutableLiveData<DiagnosisModule>();
    public MutableLiveData<String> mErrorMsg = new MutableLiveData<String>();

    private static String TAG = "DiagnosisMainViewModel";


}
