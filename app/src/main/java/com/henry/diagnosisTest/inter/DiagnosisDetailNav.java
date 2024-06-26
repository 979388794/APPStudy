package com.henry.diagnosisTest.inter;



import com.henry.diagnosisTest.model.DiagnosisInfoList;

import java.util.List;



public interface DiagnosisDetailNav extends BaseNav {

    public void initMagicIndicator(int position,List<DiagnosisInfoList> diagnosisInfoLists);


}
