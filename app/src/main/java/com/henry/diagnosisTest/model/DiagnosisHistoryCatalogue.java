package com.henry.diagnosisTest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiagnosisHistoryCatalogue implements Serializable {

    /**
     * 诊断历史目录
     */

    private String day;

    private ArrayList<DiagnosisHistoryItem> item;

    /**
     * 是否展开，默认否
     */
    private boolean isExpand = false;

    public void setDay(String day){
        this.day = day;
    }

    public String getDay(){
        return this.day;
    }

    public void setItem(ArrayList<DiagnosisHistoryItem> item){
        this.item = item;
    }

    public List<DiagnosisHistoryItem> getItem(){
        return this.item;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    @Override
    public String toString() {
        return "DiagnosisHistoryCatalogue{" +
                "day='" + day + '\'' +
                ", item=" + item +
                ", isExpand=" + isExpand +
                '}';
    }
}
