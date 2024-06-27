package com.henry.diagnosisTest.viewMdodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.henry.diagnosisTest.communication.CommunicationModule;
import com.henry.diagnosisTest.communication.CommunicationUpload;
import com.henry.diagnosisTest.communicationImp.ComuniCationBuilerFactory;
import com.henry.diagnosisTest.communicationImp.ModuleBuilder;
import com.henry.diagnosisTest.inter.DiagnosisUploadLogNav;
import com.henry.diagnosisTest.listener.OnTboxDataChangeListener;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.utils.DDSManager;
import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.CommunicationObservable;
import com.quectel.communication.CommunicationObserver;
import com.quectel.communication.ProgressListener;
import com.quectel.communication.ResponseCallBack;
import com.quectel.communication.model.ResSerializableBean;

import java.util.ArrayList;

public class DiagnosisUploadLogViewModel extends BaseViewModel<DiagnosisUploadLogNav> {
    public MutableLiveData<ArrayList<DiagnosisModule>> diagnosisModulelist = new MutableLiveData<ArrayList<DiagnosisModule>>();
    public MutableLiveData<String> mErrorMsg = new MutableLiveData<String>();
    public MutableLiveData<String> mSuccessMsg = new MutableLiveData<String>();
    private OnTboxDataChangeListener mOnTboxDataChangeListener;
    private DiagnosisModule mDiagnosisModule;
    private String mParam;
    private Context mContext;
    String TAG = getClass().getSimpleName();

    /**
     * 初始化监听器
     */
    private void initListener() {
        if (null == mOnTboxDataChangeListener) {
            mOnTboxDataChangeListener = new OnTboxDataChangeListener() {
                @Override
                public void onTboxConnect(boolean state) {
                    Log.d(TAG, "getDiagnosisInfoCycle onTboxConnect state = " + state);
                    if (state) {
                        DDSManager.getInstance().setTboxDiagnosticByListener(mParam);
                    } else {
                        mErrorMsg.postValue("链接异常，请重新诊断！");
                    }
                }

                @Override
                public void onTboxDataChange(String data) {
                    Log.d(TAG, "getDiagnosisInfoCycle onTboxDataChange");
                    getUploadLog(mDiagnosisModule, mContext, mParam);
                }
            };
        }
    }

    /**
     * 获取诊断模块的集合
     *
     * @param context
     */
    public void getDiagnosisModuleList(Context context) {

        CommunicationObservable.getInstance().getObservable(new ModuleBuilder(context,
                new CommunicationModule()), new CommunicationObserver(new ResponseCallBack<ResSerializableBean<ArrayList<DiagnosisModule>>>() {

            @Override
            public void onSuccess(ResSerializableBean<ArrayList<DiagnosisModule>> diagnosisModules) {
                Log.d(TAG, "getDiagnosisModuleList= " + "\n" + diagnosisModules.toString());
                diagnosisModulelist.setValue(diagnosisModules.getData());
            }

            @Override
            public void onFault(int code, ResSerializableBean<ArrayList<DiagnosisModule>> diagnosisModules, String errorMsg) {
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
    }


    /**
     * 上传日志
     */
    public void UploadLog(DiagnosisModule diagnosisModule, Context context, String param) {
        getNavigator().showLoading();
        mDiagnosisModule = diagnosisModule;
        mContext = context;
        mParam = param;
        initListener();
        DDSManager.getInstance().addOnTboxDataChangeListener(mOnTboxDataChangeListener);
    }

    /**
     * 获取日志
     */
    private void getUploadLog(DiagnosisModule diagnosisModule, Context mContext, String param) {
        CommunicationBuilderBase builder = ComuniCationBuilerFactory.getCommunicationBuilder
                (new CommunicationUpload(), mContext, diagnosisModule, param);
        Log.d(TAG, "getUploadLog--------------");

        CommunicationObservable.getInstance().getObservable(builder, new CommunicationObserver
                (new ResponseCallBack<ResSerializableBean<String>>() {
                    @Override
                    public void onSuccess(ResSerializableBean<String> diagnosisModules) {
                        if (param.indexOf("start_qxdm") != -1) {
                            mSuccessMsg.setValue("打开QXDM日志成功");
                        }
                        if (param.indexOf("stop_qxdm") != -1) {
                            mSuccessMsg.setValue("关闭QXDM日志成功");
                        }
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
        Log.d(TAG, "getUploadLog--------------");
        DDSManager.getInstance().removeOnTboxDataChangeListener(mOnTboxDataChangeListener);
    }

    /**
     * 重置监听器
     */
    @Override
    public void resetListener() {
        super.resetListener();
        if (null != mOnTboxDataChangeListener) {
            DDSManager.getInstance().removeOnTboxDataChangeListener(mOnTboxDataChangeListener);
            mOnTboxDataChangeListener = null;
        }
    }
}
