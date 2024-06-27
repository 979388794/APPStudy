package com.henry.diagnosisTest.viewMdodel;



import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import com.henry.diagnosisTest.communication.CommunicationHistory;
import com.henry.diagnosisTest.communication.CommunicationHistoryItem;
import com.henry.diagnosisTest.communicationImp.ComuniCationBuilerFactory;
import com.henry.diagnosisTest.inter.DiagnosisHistoryNav;
import com.henry.diagnosisTest.listener.OnTboxDataChangeListener;
import com.henry.diagnosisTest.model.DiagnosisHistoryCatalogue;
import com.henry.diagnosisTest.model.DiagnosisInfo;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.utils.DDSManager;
import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.CommunicationObservable;
import com.quectel.communication.CommunicationObserver;
import com.quectel.communication.ProgressListener;
import com.quectel.communication.ResponseCallBack;
import com.quectel.communication.model.ResSerializableBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class DiagnosisHistoryViewModel extends BaseViewModel<DiagnosisHistoryNav> {
    public MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisHistoryCatalogue>>>> listMutableLiveHisRootData = new MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisHistoryCatalogue>>>>();
    public MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>> listMutableLiveHisInfoData = new MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>();
    public MutableLiveData<String> mErrorMsg = new MutableLiveData<>();
    public HashMap<String, Object> hisTypes = new LinkedHashMap<String, Object>();
    private HashMap<String, Object> param = new LinkedHashMap<String, Object>();
    private Context mContext;
    private DiagnosisModule mDiagnosisModule;
    private OnTboxDataChangeListener mOnTboxDataChangeListener = null;
    private int currentType =-1;

    private void initHashMapData(String reqType,String dayTime){
        if(param.size() != 0){
            param.clear();
        }
        if(currentType == 1){
            if(hisTypes.size() != 0) {
                hisTypes.clear();
            }
            if(param.size() != 0){
                param.clear();
            }
            hisTypes.put("type",reqType);
            hisTypes.put("diag_id","00");

            param.put("cmd", "diag_history_upload");
            param.put("args",hisTypes);

        }else if(currentType == 2){
            if(hisTypes.size() != 0) {
                hisTypes.clear();
            }
            if(param.size() != 0){
                param.clear();
            }
            hisTypes.put("type",reqType);
            hisTypes.put("diag_id",dayTime);

            param.put("cmd", "diag_history_upload");
            param.put("args",hisTypes);
        }
    }

    private void initListener(){
        if(null == mOnTboxDataChangeListener) {
            mOnTboxDataChangeListener = new OnTboxDataChangeListener() {
                @Override
                public void onTboxConnect(boolean state) {
                    Log.d(TAG,"getDiagnosisEventInfo onTboxConnect state = " + state);
                    if(state) {
                        DDSManager.getInstance().setTboxDiagnosticByListener(new Gson().toJson(param));
                    }else {
                        mErrorMsg.setValue("链接异常，请重新诊断！");
                    }
                }

                @Override
                public void onTboxDataChange(String data) {
                    if (currentType == 1){
                        getDiagnosisHistoryCatalogueImp();
                    }else if(currentType == 2){
                        getDiagnosisHistoryItemImp();
                    }
                }
            };
        }
    }

    public void getDiagnosisHistoryCatalogue(DiagnosisModule diagnosisModule, Context context,String reqType) {
        getNavigator().showLoading();
        mContext = context;
        mDiagnosisModule = diagnosisModule;
        currentType = 1;
        initHashMapData(reqType,null);
        initListener();
        DDSManager.getInstance().addOnTboxDataChangeListenerTest(3,mOnTboxDataChangeListener);
//        DDSManager.getInstance().addOnTboxDataChangeListener(mOnTboxDataChangeListener);
    }

    private void getDiagnosisHistoryCatalogueImp() {

        CommunicationBuilderBase builder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationHistory(), mContext, mDiagnosisModule, new Gson().toJson(param));
        CommunicationObservable.getInstance().getObservable(builder, new CommunicationObserver(new ResponseCallBack<ResSerializableBean<ArrayList<DiagnosisHistoryCatalogue>>>() {

            @Override
            public void onSuccess(ResSerializableBean<ArrayList<DiagnosisHistoryCatalogue>> resSerializableBeanRoot) {
                ArrayList<ResSerializableBean<ArrayList<DiagnosisHistoryCatalogue>>> value = new ArrayList<ResSerializableBean<ArrayList<DiagnosisHistoryCatalogue>>>();
                value.add(resSerializableBeanRoot);
                listMutableLiveHisRootData.postValue(value);
            }

            @Override
            public void onFault(int code, ResSerializableBean<ArrayList<DiagnosisHistoryCatalogue>> arrayListResSerializableBean, String errorMsg) {
                mErrorMsg.setValue(errorMsg);
            }

        }, new ProgressListener() {

            @Override
            public void startProgress() {
                if (null != getNavigator()) {
                    getNavigator().showLoading();
                }
            }

            @Override
            public void cancelProgress() {
                if (null != getNavigator()) {
                    getNavigator().hideLoading();
                }
            }
        }));

        DDSManager.getInstance().removeOnTboxDataChangeListener(mOnTboxDataChangeListener);
        currentType = -1;
    }

    public void getDiagnosisHistoryItem(DiagnosisModule diagnosisModule, Context context, String reqType, String dayTime){
        getNavigator().showLoading();
        mContext = context;
        mDiagnosisModule = diagnosisModule;
        currentType = 2;
        initHashMapData(reqType,dayTime);
        initListener();
        DDSManager.getInstance().addOnTboxDataChangeListenerTest(3,mOnTboxDataChangeListener);
        DDSManager.getInstance().addOnTboxDataChangeListener(mOnTboxDataChangeListener);
    }

    private void getDiagnosisHistoryItemImp() {
        CommunicationBuilderBase builder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationHistoryItem(mDiagnosisModule.getId()), mContext, mDiagnosisModule, new Gson().toJson(param));
        CommunicationObservable.getInstance().getObservable(builder, new CommunicationObserver(new ResponseCallBack<ResSerializableBean<ArrayList<DiagnosisInfoList>>>() {

            @Override
            public void onSuccess(ResSerializableBean<ArrayList<DiagnosisInfoList>> diagnosisHistoryInfos) {
                if (mDiagnosisModule.getId() == diagnosisHistoryInfos.getId()) {
                    mDiagnosisModule.setShowInfo(diagnosisHistoryInfos.getCode() == 0);
                    mDiagnosisModule.setInfoLists(diagnosisHistoryInfos.getData());
                    mDiagnosisModule.setStatusCode(0);
                    if (diagnosisHistoryInfos.getCode() != 0) {
                        mDiagnosisModule.setStatusCode(2);
                    } else {
                        for (DiagnosisInfoList datumDatum : diagnosisHistoryInfos.getData()) {
                            for (DiagnosisInfo diagnosisInfo : datumDatum.getDiagnosisContentList()) {
                                if (diagnosisInfo.getDiagnosisStatusCode() != 0) {
                                    mDiagnosisModule.setStatusCode(1);
                                    datumDatum.setStatusCode(1);
                                }
                            }
                        }
                    }
                }

                ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> value = new ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>();
                value.add(diagnosisHistoryInfos);
                listMutableLiveHisInfoData.postValue(value);
            }

            @Override
            public void onFault(int code, ResSerializableBean<ArrayList<DiagnosisInfoList>> arrayListResSerializableBean, String errorMsg) {
                mErrorMsg.setValue(errorMsg);
            }

        }, new ProgressListener() {

            @Override
            public void startProgress() {
                if (null != getNavigator()) {
                    getNavigator().showLoading();
                }
            }

            @Override
            public void cancelProgress() {
                if (null != getNavigator()) {
                    getNavigator().hideLoading();
                }
            }
        }));
        DDSManager.getInstance().removeOnTboxDataChangeListener(mOnTboxDataChangeListener);
        currentType = -1;
    }

    @Override
    public void resetListener() {
        super.resetListener();
        if(null != mOnTboxDataChangeListener){
            DDSManager.getInstance().removeOnTboxDataChangeListener(mOnTboxDataChangeListener);
            mOnTboxDataChangeListener = null;
        }
        currentType = -1;
    }
}
