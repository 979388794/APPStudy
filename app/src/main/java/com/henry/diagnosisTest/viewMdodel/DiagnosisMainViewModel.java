package com.henry.diagnosisTest.viewMdodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.henry.DialogTest.DialogTestActivity;
import com.henry.diagnosisTest.communication.CommunicationModule;
import com.henry.diagnosisTest.communicationImp.ModuleBuilder;
import com.henry.diagnosisTest.inter.DiagnosisMainNav;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.quectel.communication.CommunicationObservable;
import com.quectel.communication.CommunicationObserver;
import com.quectel.communication.ProgressListener;
import com.quectel.communication.ResponseCallBack;
import com.quectel.communication.model.ResSerializableBean;

import java.util.ArrayList;

/**
 * @author: henry.xue
 * @date: 2024-06-18
 */
public class DiagnosisMainViewModel extends BaseViewModel<DiagnosisMainNav> {

    String TAG = getClass().getSimpleName();
    public MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>> listMutableLiveData = new MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>();
    public MutableLiveData<ArrayList<DiagnosisModule>> diagnosisModulelist = new MutableLiveData<ArrayList<DiagnosisModule>>();
    public MutableLiveData<Boolean> isShowResult = new MutableLiveData<>(false);
    public MutableLiveData<String> mErrorMsg = new MutableLiveData<>();


    /**
     * 获取json文件并初始化 diagnosisModulelist
     */
    public void getDiagnosisModuleList(Context context) {
        CommunicationObservable.getInstance().getObservable
                (new ModuleBuilder(context, new CommunicationModule()),
                        new CommunicationObserver(
                                new ResponseCallBack<ResSerializableBean<ArrayList<DiagnosisModule>>>() {
                                    @Override
                                    public void onSuccess(ResSerializableBean<ArrayList<DiagnosisModule>> diagnosisModules) {
                                        Log.d(TAG, "onSuccess :" + diagnosisModules);
                                        Log.d(TAG, "onSuccess getData :" + diagnosisModules.getData());
                                        diagnosisModulelist.setValue(diagnosisModules.getData());
                                    }

                                    @Override
                                    public void onFault(int code, ResSerializableBean<ArrayList<DiagnosisModule>> diagnosisModules, String errorMsg) {
                                        mErrorMsg.setValue(errorMsg);
//                                        Toast.makeText(context,
//                                                "获取json文件失败",
//                                                Toast.LENGTH_SHORT).show();
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


}
