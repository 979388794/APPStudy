package com.henry.diagnosisTest.communicationImp;

import android.content.Context;
import android.util.Log;

import com.henry.diagnosisTest.model.DiagnosisModule;
import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.CommunicationDefinition;



/**
 * 通信创建工厂
 */
public class ComuniCationBuilerFactory {

    private static final String TAG = "ComuniCationBuilerFactory";

    public static CommunicationBuilderBase getCommunicationBuilder(CommunicationDefinition communicationDefinition,
                                                                   Context mContext, DiagnosisModule diagnosisModule,
                                                                   /**HashMap<String, String>**/String param) {

        String moduleType = diagnosisModule.getModuleType();
        CommunicationBuilderBase builder = null;
        Log.d(TAG, "moduleType = " + moduleType);
        if ("ZMQ".equals(moduleType)) {
            builder = new ZMQMainBuilder(communicationDefinition, "tcp://" +
                    diagnosisModule.getModuleIP() + ":" + diagnosisModule.getModulePort(), param);
        } else if ("DDS".equals(moduleType)) {
            builder = new DDSMainBuilder(communicationDefinition, mContext, "tcp://" +
                    diagnosisModule.getModuleIP() + ":" + diagnosisModule.getModulePort(), param);
        } else if ("LOCAL".equals(moduleType)) {
            builder = new LocalBuilder(communicationDefinition);
        }
        return builder;
    }

}
