package com.henry.diagnosisTest.communication;


import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.quectel.communication.CommunicationDefinitionIpm;
import com.quectel.communication.model.ResSerializableBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.functions.BiFunction;


public class CommunicationMain extends CommunicationDefinitionIpm<ResSerializableBean<ArrayList<DiagnosisInfoList>>> {

    private int id;

    public CommunicationMain(int id) {
        this.id = id;
    }

    @Override
    public ResSerializableBean<ResSerializableBean<ArrayList<DiagnosisInfoList>>> getData(String data) {
        ResSerializableBean resSerializableBean =super.getData(data);
        if(resSerializableBean != null) {
            resSerializableBean.setId(id);
        }
        return resSerializableBean;
    }


    @Override
    public BiFunction getBiFunction() {
        return new BiFunction<ResSerializableBean, ResSerializableBean, ResSerializableBean>() {

            @Override
            public ResSerializableBean apply(ResSerializableBean s, ResSerializableBean s1) throws Throwable {
                ResSerializableBean arrayListResSerializableBean = null;
                if (s.isZip() || s1.isZip()) {
                    arrayListResSerializableBean = s;
                    ((List) arrayListResSerializableBean.getData()).add(s1);
                } else {
                    arrayListResSerializableBean = new ResSerializableBean<>();
                    ArrayList<ResSerializableBean> resSerializableBeans = new ArrayList<>();
                    resSerializableBeans.add(s);
                    resSerializableBeans.add(s1);
                    arrayListResSerializableBean.setCode(0);
                    arrayListResSerializableBean.setData(resSerializableBeans);
                    arrayListResSerializableBean.setZip(true);
                }
                return arrayListResSerializableBean;
            }
        };
    }
}
