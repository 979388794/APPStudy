package com.henry.diagnosisTest.communicationImp;

import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.CommunicationDefinition;
import com.quectel.communication.util.DeviceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Android 本地检查网络
 */
public class LocalBuilder extends CommunicationBuilderBase {

    /**
     *
     * @param communicationDefinition
     */
    public LocalBuilder(CommunicationDefinition communicationDefinition) {
        super(communicationDefinition);
    }

    /**
     * 提供具体的通信动作
     * 返回 本地 JSONObject 对象
     */
    public String getCommunicationAction() {
        int pingResult = DeviceUtil.pingNet();
        int pingIPResult = DeviceUtil.pingIPNet();

        JSONObject temp = new JSONObject();
        try {
            temp.put("code", 0);
            JSONArray data = new JSONArray();
            JSONObject diagnosis = new JSONObject();
            diagnosis.put("diagnosisName", "本地网络");
            JSONArray diagnosisContentList = new JSONArray();
            JSONObject diagnosisContentListitemIP = new JSONObject();
            diagnosisContentListitemIP.put("diagnosisContentName", "本地PING网(IP)");
            diagnosisContentListitemIP.put("diagnosisStatusCode", pingIPResult == 0 ? 0 : 1);
            diagnosisContentListitemIP.put("diagnosisStatusInfo", pingIPResult == 0 ? "本地网络通畅" : "本地网络不同");
            diagnosisContentListitemIP.put("diagnosisSuggestion", "");
            diagnosisContentList.put(diagnosisContentListitemIP);
            //if (pingResult == 0) {
                JSONObject diagnosisContentListitem = new JSONObject();
                diagnosisContentListitem.put("diagnosisContentName", "本地PING网(域名)");
                diagnosisContentListitem.put("diagnosisStatusCode", pingResult == 0 ? 0 : 1);
                diagnosisContentListitem.put("diagnosisStatusInfo", pingResult == 0 ? "本地网络通畅" : "本地网络不同,请检查DNS配置是否正确");
                diagnosisContentListitem.put("diagnosisSuggestion", "");
                diagnosisContentList.put(diagnosisContentListitem);
           // }

            diagnosis.put("diagnosisContentList", diagnosisContentList);
            data.put(diagnosis);
            temp.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp.toString();
    }


}
