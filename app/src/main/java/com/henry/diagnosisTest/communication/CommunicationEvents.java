package com.henry.diagnosisTest.communication;


import com.henry.diagnosisTest.model.DiagnosisEventRoot;
import com.quectel.communication.CommunicationDefinitionIpm;
import com.quectel.communication.model.ResSerializableBean;

import java.util.ArrayList;

public class CommunicationEvents extends CommunicationDefinitionIpm<ResSerializableBean<ArrayList<DiagnosisEventRoot>>> {
}
