package com.henry.diagnosisTest.inter;



import com.henry.diagnosisTest.model.DiagnosisInfoList;

import java.util.List;

/**
 * Created by callen.ye
 * on 2022/1/20
 */
public interface DiagnosisViewpagerDetailNav extends BaseNav {

    public void initViewPager(List<DiagnosisInfoList> infoLists);


}
