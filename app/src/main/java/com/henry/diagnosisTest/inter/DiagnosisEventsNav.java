package com.henry.diagnosisTest.inter;



import com.henry.diagnosisTest.model.DiagnosisEvent;

import java.util.ArrayList;



public interface DiagnosisEventsNav extends BaseNav {

    public void initRecycleView();
    //public void loadData(DiagnosisEventRoot diagnosisEventRoot);
    public void loadData(ArrayList<DiagnosisEvent> eventArrayList);

}
