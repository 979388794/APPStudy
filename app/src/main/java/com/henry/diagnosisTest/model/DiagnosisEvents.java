package com.henry.diagnosisTest.model;

import java.io.Serializable;
import java.util.List;

public class DiagnosisEvents implements Serializable {

    /**
     * 诊断事件s
     */
    private List<DiagnosisEventRoot> data;

    public void setData(List<DiagnosisEventRoot> events){
        this.data = events;
    }
    public List<DiagnosisEventRoot> getData(){
        return this.data;
    }

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

    @Override
    public String toString() {
        return "DiagnosisEvents{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
