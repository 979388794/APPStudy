package com.henry.diagnosisTest.model;

import java.io.Serializable;
import java.util.List;
public class DiagnosisEventRoot implements Serializable
{
    /**
     * 诊断事件根源
     */
    private List<DiagnosisEvent> events;

    public void setEvents(List<DiagnosisEvent> events){
        this.events = events;
    }
    public List<DiagnosisEvent> getEvents(){
        return this.events;
    }

    @Override
    public String toString() {
        return "DiagnosisEventRoot{" +
                "events=" + events +
                '}';
    }
}
