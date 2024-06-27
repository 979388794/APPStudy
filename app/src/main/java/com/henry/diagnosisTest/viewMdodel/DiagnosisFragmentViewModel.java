package com.henry.diagnosisTest.viewMdodel;



import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import com.henry.diagnosisTest.base.BaseViewModel;
import com.henry.diagnosisTest.communication.CommunicationFix;
import com.henry.diagnosisTest.communication.CommunicationUpload;
import com.henry.diagnosisTest.communicationImp.ComuniCationBuilerFactory;
import com.henry.diagnosisTest.inter.DiagnosisFragmentNav;
import com.henry.diagnosisTest.listener.OnTboxDataChangeListener;
import com.henry.diagnosisTest.model.DiagnosisInfo;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.utils.DDSManager;
import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.CommunicationObservable;
import com.quectel.communication.CommunicationObserver;
import com.quectel.communication.ProgressListener;
import com.quectel.communication.ResponseCallBack;
import com.quectel.communication.model.ResSerializableBean;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class DiagnosisFragmentViewModel extends BaseViewModel<DiagnosisFragmentNav> {

    public MutableLiveData<String> mErrorMsg = new MutableLiveData<String>();
    public MutableLiveData<String> mSuccessMsg = new MutableLiveData<String>();
    public MutableLiveData<DiagnosisInfo> mDiagnosisInfo = new MutableLiveData<DiagnosisInfo>();
    private String TAG = "DiagnosisFragmentViewModel";
    private OnTboxDataChangeListener mOnTboxDataChangeListener;
    private HashMap<String, Object> param = new LinkedHashMap<String, Object>();
    private int currentType =-1;
    private CommunicationBuilderBase fixBuilder,upLogBuilder;
    private Context mContext;
    private DiagnosisModule mDiagnosisModule;

    private void initHashMapData(){
        if(param.size() != 0){
            param.clear();
        }
        if(currentType == 1){
            param.put("cmd", "fix");
        }else if(currentType == 2){
            param.put("cmd", "log_upload");
        }
    }

    private void initBuilder(){
        if(null == fixBuilder){
            fixBuilder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationFix(), mContext,mDiagnosisModule, new Gson().toJson(param));
        }
        if(null == upLogBuilder){
            upLogBuilder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationUpload(), mContext,mDiagnosisModule, new Gson().toJson(param));
        }
    }

    private void initListener(){
        initHashMapData();
        initBuilder();
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
                        getFixDiagnosis(fixBuilder);
                    }else if(currentType == 2){
                        getUploadLogDiagnosis(upLogBuilder);
                    }
                }
            };
        }
    }

    public void FixDiagnosis(DiagnosisModule diagnosisModule, Context context) {
        getNavigator().showLoading();
        currentType = 1;
        initListener();
        mContext = context;
        mDiagnosisModule = diagnosisModule;
        DDSManager.getInstance().addOnTboxDataChangeListener(mOnTboxDataChangeListener);
    }

    private void getFixDiagnosis(CommunicationBuilderBase builder) {

        CommunicationObservable.getInstance().getObservable(builder, new CommunicationObserver(new ResponseCallBack<ResSerializableBean<DiagnosisInfo>>() {

            @Override
            public void onSuccess(ResSerializableBean<DiagnosisInfo> diagnosisInfo) {
                mDiagnosisInfo.setValue(diagnosisInfo.getData());
            }

            @Override
            public void onFault(int code, ResSerializableBean<DiagnosisInfo> diagnosisModules, String errorMsg) {
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


    public void UploadLogDiagnosis(DiagnosisModule diagnosisModule,Context context) {
        getNavigator().showLoading();
        currentType = 2;
        mContext = context;
        mDiagnosisModule = diagnosisModule;
        DDSManager.getInstance().addOnTboxDataChangeListener(mOnTboxDataChangeListener);
    }

    private void getUploadLogDiagnosis(CommunicationBuilderBase builder) {
        CommunicationObservable.getInstance().getObservable(builder, new CommunicationObserver(new ResponseCallBack<ResSerializableBean<String>>() {

            @Override
            public void onSuccess(ResSerializableBean<String> diagnosisModules) {
                mSuccessMsg.setValue("上传日志成功");
            }

            @Override
            public void onFault(int code, ResSerializableBean<String> diagnosisModules, String errorMsg) {
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
