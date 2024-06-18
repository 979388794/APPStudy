package com.henry.diagnosisTest.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DiagnosisKkLogBean implements Serializable {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return ":" + value;
    }

}
