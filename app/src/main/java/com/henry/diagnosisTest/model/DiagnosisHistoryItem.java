package com.henry.diagnosisTest.model;

import java.io.Serializable;

public class DiagnosisHistoryItem implements Serializable {

    /**
     * 诊断历史项目
     */
    private String time;

    private String state;

    private int diag_index;

    private int diag_len;

    /**
     * 该子级类所对应父级类的名称
     */
    protected String parentName;

    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setStatus(String status){
        this.state = status;
    }
    public String getStatus(){
        return this.state;
    }

    public int getDiag_index() {
        return diag_index;
    }

    public void setDiag_index(int diag_index) {
        this.diag_index = diag_index;
    }

    public int getDiag_len() {
        return diag_len;
    }

    public void setDiag_len(int diag_len) {
        this.diag_len = diag_len;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "DiagnosisHistoryItem{" +
                "time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", diag_index=" + diag_index +
                ", diag_len=" + diag_len +
                ", parentName='" + parentName + '\'' +
                '}';
    }
}
