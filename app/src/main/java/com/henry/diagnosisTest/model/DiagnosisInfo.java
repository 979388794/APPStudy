package com.henry.diagnosisTest.model;

import java.io.Serializable;


public class DiagnosisInfo implements Serializable {

    /**
     * 诊断信息
     */
    private String Name;
    private int ID;
    private String Info;
    //-1没有诊断 0诊断正常 1诊断有问题
    private int StatusCode;
    private String Suggestion;
    //private int diagnosisIsRepair;


    public int getId() {
        return ID;
    }

    public void setId(int id) {
        this.ID = id;
    }

    //public int getDiagnosisIsRepair() {
    //    return diagnosisIsRepair;
    //}
    //
    //public void setDiagnosisIsRepair(int diagnosisIsRepair) {
    //    this.diagnosisIsRepair = diagnosisIsRepair;
    //}

    public String getDiagnosisStatusInfo() {
        return Info;
    }

    public void setDiagnosisStatusInfo(String diagnosisStatusInfo) {
        this.Info = diagnosisStatusInfo;
    }

    public Integer getDiagnosisStatusCode() {
        return StatusCode;
    }

    public void setDiagnosisStatusCode(Integer diagnosisStatusCode) {
        this.StatusCode = diagnosisStatusCode;
    }

    public String getDiagnosisSuggestion() {
        return Suggestion;
    }

    public void setDiagnosisSuggestion(String diagnosisSuggestion) {
        this.Suggestion = diagnosisSuggestion;
    }

    public String getDiagnosisContentName() {
        return Name;
    }

    public void setDiagnosisContentName(String diagnosisContentName) {
        this.Name = diagnosisContentName;
    }

    @Override
    public String toString() {
        return "DiagnosisInfo{" +
                "Name='" + Name + '\'' +
                ", ID=" + ID +
                ", Info='" + Info + '\'' +
                ", StatusCode=" + StatusCode +
                ", Suggestion='" + Suggestion + '\'' +
                '}';
    }
}
