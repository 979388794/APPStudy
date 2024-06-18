package com.henry.diagnosisTest.model;

import java.io.Serializable;
import java.util.ArrayList;


public class DiagnosisHistoryInfo implements Serializable {

    /**
     * 诊断历史信息
     */
    private String diagnosisHistory;
    private ArrayList<DiagnosisInfoList> diagnosisHistoryData;

    public String getDiagnosisHistory() {
        return diagnosisHistory;
    }

    public void setDiagnosisHistory(String diagnosisHistory) {
        this.diagnosisHistory = diagnosisHistory;
    }

    public ArrayList<DiagnosisInfoList> getDiagnosisHistoryData() {
        return diagnosisHistoryData;
    }

    public void setDiagnosisHistoryData(ArrayList<DiagnosisInfoList> diagnosisHistoryData) {
        this.diagnosisHistoryData = diagnosisHistoryData;
    }

    @Override
    public String toString() {
        return "DiagnosisHistoryInfo{" +
                "diagnosisHistory='" + diagnosisHistory + '\'' +
                ", diagnosisHistoryData=" + diagnosisHistoryData +
                '}';
    }
}
