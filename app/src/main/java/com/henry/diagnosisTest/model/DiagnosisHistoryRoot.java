package com.henry.diagnosisTest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiagnosisHistoryRoot implements Serializable {
    /**
     * 诊断历史根源
     */
    private ArrayList<DiagnosisHistoryCatalogue> data;
    private String day_num;
    private int code;
    private String message;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setDay_num(String day_num){
        this.day_num = day_num;
    }
    public String getDay_num(){
        return this.day_num;
    }
    public void setCatalogue(ArrayList<DiagnosisHistoryCatalogue> catalogue){
        this.data = catalogue;
    }
    public List<DiagnosisHistoryCatalogue> getCatalogue(){
        return this.data;
    }
}
