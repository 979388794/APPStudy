package com.henry.diagnosisTest.viewMdodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import com.henry.diagnosisTest.communication.CommunicationEvents;
import com.henry.diagnosisTest.communication.CommunicationHistoryItem;
import com.henry.diagnosisTest.communicationImp.ComuniCationBuilerFactory;
import com.henry.diagnosisTest.inter.DiagnosisEventsNav;
import com.henry.diagnosisTest.listener.OnTboxDataChangeListener;
import com.henry.diagnosisTest.model.DiagnosisEventRoot;
import com.henry.diagnosisTest.model.DiagnosisEvents;
import com.henry.diagnosisTest.model.DiagnosisInfo;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.utils.DDSManager;
import com.henry.diagnosisTest.utils.GsonUtils;
import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.CommunicationObservable;
import com.quectel.communication.CommunicationObserver;
import com.quectel.communication.ProgressListener;
import com.quectel.communication.ResponseCallBack;
import com.quectel.communication.model.ResSerializableBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DiagnosisEventsViewModel extends BaseViewModel<DiagnosisEventsNav> {

    public MutableLiveData<ResSerializableBean<ArrayList<DiagnosisEventRoot>>> eventRootData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>> eventItemData = new MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>();

    public MutableLiveData<String> mErrorMsg = new MutableLiveData<>();
    private String backData = null;
    public HashMap<String, Object> eventTypes = new LinkedHashMap<String, Object>();
    private HashMap<String, Object> param = new LinkedHashMap<String, Object>();
    private int sum = 0;
    private Context mContext;
    private DiagnosisModule mDiagnosisModule;
    private OnTboxDataChangeListener mOnTboxDataChangeListener = null;
    private int currentType = -1;
    //获取条目点击数据
    private static String event_item = "item";

    public DiagnosisEventsViewModel() {

    }

    private void initHashMapData(String dayTime) {
        if (currentType == 1) {
            if (param.size() != 0) {
                param.clear();
            }
            param.put("cmd", "diag_event_upload");
        } else if (currentType == 2) {
            if (eventTypes.size() != 0) {
                eventTypes.clear();
            }
            if (param.size() != 0) {
                param.clear();
            }
            eventTypes.put("type", event_item);
            eventTypes.put("diag_id", dayTime);

            param.put("cmd", "diag_history_upload");
            param.put("args", eventTypes);
        }
    }

    private void initListener() {
        if (null == mOnTboxDataChangeListener) {
            mOnTboxDataChangeListener = new OnTboxDataChangeListener() {
                @Override
                public void onTboxConnect(boolean state) {
                    Log.d(TAG, "getDiagnosisEventInfo onTboxConnect state = " + state);
                    if (state) {
                        DDSManager.getInstance().setTboxDiagnosticByListener(new Gson().toJson(param));
                    } else {
                        mErrorMsg.setValue("链接异常，请重新诊断！");
                    }
                }

                @Override
                public void onTboxDataChange(String data) {
                    if (currentType == 1) {
                        backData = data;
                        getDiagnosisEventsImp();
                    } else if (currentType == 2) {
                        getDiagnosisEventsItemImp(mDiagnosisModule, mContext);
                    }
                }
            };
        }
    }

    //获取all events数据
    public void getDiagnosisEvents(DiagnosisModule diagnosisModule, Context context) {
        Log.d(TAG, "getDiagnosisEvents");
        getNavigator().showLoading();
        currentType = 1;
        initHashMapData(null);
        initListener();
        mContext = context;
        mDiagnosisModule = diagnosisModule;
        DDSManager.getInstance().addOnTboxDataChangeListenerTest(2, mOnTboxDataChangeListener);
//        DDSManager.getInstance().addOnTboxDataChangeListener(mOnTboxDataChangeListener);
    }

    private void getDiagnosisEventsImpTest() {
        ResSerializableBean<DiagnosisEventRoot> resSerializableBeanRoot = new ResSerializableBean<>();
        DiagnosisEvents root = GsonUtils.GsonToBean(backData, DiagnosisEvents.class);
        Log.d(TAG, "getDiagnosisEventsImpTest root = " + root);
        List<DiagnosisEventRoot> dataList = root.getData();
        Log.d(TAG, "getDiagnosisEventsImpTest dataList = " + dataList);
        DiagnosisEventRoot diagnosisEventRoot = dataList.get(0);
        Log.d(TAG, "getDiagnosisEventsImpTest  data = " + diagnosisEventRoot);
        Log.d(TAG, "getDiagnosisEventsImpTest getEvents= " + diagnosisEventRoot.getEvents());
        //resSerializableBeanRoot.setData(diagnosisEventRoot);
        //eventRootData.postValue(resSerializableBeanRoot);
    }

    public void getDiagnosisEventsImp() {
        CommunicationBuilderBase builder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationEvents(), mContext, mDiagnosisModule, new Gson().toJson(param));
        CommunicationObservable.getInstance().getObservable(builder, new CommunicationObserver(new ResponseCallBack<ResSerializableBean<ArrayList<DiagnosisEventRoot>>>() {

            @Override
            public void onSuccess(ResSerializableBean<ArrayList<DiagnosisEventRoot>> resSerializableBeanRoot) {
                Log.d(TAG, "onSuccess bean = " + resSerializableBeanRoot);
                eventRootData.postValue(resSerializableBeanRoot);
            }

            @Override
            public void onFault(int code, ResSerializableBean<ArrayList<DiagnosisEventRoot>> arrayListResSerializableBean, String errorMsg) {
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

    public void getDiagnosisEventsItem(DiagnosisModule diagnosisModule, Context mContext, String dayTime) {
        getNavigator().showLoading();
        currentType = 2;
        mDiagnosisModule = diagnosisModule;
        initHashMapData(dayTime);
        initListener();
//        DDSManager.getInstance().addOnTboxDataChangeListener(mOnTboxDataChangeListener);
        DDSManager.getInstance().addOnTboxDataChangeListenerTest(2,mOnTboxDataChangeListener);
    }

    private void getDiagnosisEventsItemImp(DiagnosisModule diagnosisModule, Context context) {
        CommunicationBuilderBase builder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationHistoryItem(diagnosisModule.getId()), context, diagnosisModule, new Gson().toJson(param));
        CommunicationObservable.getInstance().getObservable(builder, new CommunicationObserver(new ResponseCallBack<ResSerializableBean<ArrayList<DiagnosisInfoList>>>() {

            @Override
            public void onSuccess(ResSerializableBean<ArrayList<DiagnosisInfoList>> diagnosisHistoryInfos) {
                if (diagnosisModule.getId() == diagnosisHistoryInfos.getId()) {
                    diagnosisModule.setShowInfo(diagnosisHistoryInfos.getCode() == 0);
                    diagnosisModule.setInfoLists(diagnosisHistoryInfos.getData());
                    diagnosisModule.setStatusCode(0);
                    if (diagnosisHistoryInfos.getCode() != 0) {
                        diagnosisModule.setStatusCode(2);
                    } else {
                        for (DiagnosisInfoList datumDatum : diagnosisHistoryInfos.getData()) {
                            for (DiagnosisInfo diagnosisInfo : datumDatum.getDiagnosisContentList()) {
                                if (diagnosisInfo.getDiagnosisStatusCode() != 0) {
                                    diagnosisModule.setStatusCode(1);
                                    datumDatum.setStatusCode(1);
                                }
                            }
                        }
                    }
                }

                ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> value = new ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>();
                value.add(diagnosisHistoryInfos);
                eventItemData.postValue(value);
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
        if (null != mOnTboxDataChangeListener) {
            DDSManager.getInstance().removeOnTboxDataChangeListener(mOnTboxDataChangeListener);
            mOnTboxDataChangeListener = null;
        }
        currentType = -1;
    }
}
