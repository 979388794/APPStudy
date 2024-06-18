package com.henry.diagnosisTest.model;

import java.util.List;
public class DiagnosisEvent
{
    /**
     * 诊断事件
     */
    private String date;

    private List<DiagnosisEventsItem> item;

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    public void setItem(List<DiagnosisEventsItem> item){
        this.item = item;
    }
    public List<DiagnosisEventsItem> getItem(){
        return this.item;
    }

    /**
     * 是否展开，默认否
     */
    private boolean isExpand = false;

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    @Override
    public String toString() {
        return "DiagnosisEvent{" +
                "date='" + date + '\'' +
                ", item=" + item +
                ", isExpand=" + isExpand +
                '}';
    }
}
