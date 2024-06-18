package com.henry.diagnosisTest.communicationImp;

import android.content.Context;
import android.util.Log;

import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.CommunicationDefinition;

/**
 * 与底层DDS通信方式
 */
public class DDSMainBuilder extends CommunicationBuilderBase {
    private static final String TAG = "DDSMainBuilder";
    public String result;
    private Context mContext;

    /**
     *
     * @param communicationDefinition
     * @param context
     * @param ipAndProt
     * @param paramMa
     */
    public DDSMainBuilder(CommunicationDefinition communicationDefinition, Context context, String ipAndProt, String paramMa) {
        super(communicationDefinition);
        mContext = context;
        Log.d(TAG, "app->soa builder create");
    }

    /**
     * 提供具体的通信动作
     * 返回 backData
     */
    @Override
    public String getCommunicationAction() throws Exception {
        //todo  暂时省略
//        result = DDSManager.getInstance().getTboxData();
        Log.d(TAG, "getCommunicationAction");
        return result;
    }

}
