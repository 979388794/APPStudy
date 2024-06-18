package com.henry.diagnosisTest.model;

import java.io.Serializable;

public class DiagnosisEventsItem implements Serializable
{

    /**
     * 诊断事件项目
     */
    private String time;

    private int diag_index;

    private int diag_len;

    private String net_state;

    private String reason;

    protected String parentName;

    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setDiag_index(int diag_index){
        this.diag_index = diag_index;
    }
    public int getDiag_index(){
        return this.diag_index;
    }
    public void setDiag_len(int diag_len){
        this.diag_len = diag_len;
    }
    public int getDiag_len(){
        return this.diag_len;
    }
    public void setNet_state(String net_state){
        this.net_state = net_state;
    }
    public String getNet_state(){
        return this.net_state;
    }
    public void setReason(String reason){
        this.reason = reason;
    }
    public String getReason(){
        return this.reason;
    }
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "DiagnosisEventsItem{" +
                "time='" + time + '\'' +
                ", diag_index=" + diag_index +
                ", diag_len=" + diag_len +
                ", net_state='" + net_state + '\'' +
                ", reason='" + reason + '\'' +
                ", parentName='" + parentName + '\'' +
                '}';
    }
}
