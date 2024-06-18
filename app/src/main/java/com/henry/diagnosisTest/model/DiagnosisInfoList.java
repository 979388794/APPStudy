package com.henry.diagnosisTest.model;

import java.io.Serializable;
import java.util.ArrayList;


public class DiagnosisInfoList implements Serializable {


    /**
     * 诊断信息集合
     */
    private String diagnosisName;
    /**
     * 0 正常   1诊断有问题
     */
    private int statusCode;
    private ArrayList<DiagnosisInfo> diag_list;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public ArrayList<DiagnosisInfo> getDiagnosisContentList() {
        return diag_list;
    }

    public void setDiagnosisContentList(ArrayList<DiagnosisInfo> diagnosisContentList) {
        this.diag_list = diagnosisContentList;
    }

    @Override
    public String toString() {
        return "DiagnosisInfoList{" +
                "diagnosisName='" + diagnosisName + '\'' +
                ", statusCode=" + statusCode +
                ", diag_list=" + diag_list +
                '}';
    }
}
