package com.henry.diagnosisTest.inter;




import com.henry.diagnosisTest.model.DiagnosisHistoryCatalogue;

import java.util.ArrayList;



public interface DiagnosisHistoryNav extends BaseNav {

    public void initRecycleView();
    public void loadData(ArrayList<DiagnosisHistoryCatalogue> diagnosisHistoryRootDatas);

}
