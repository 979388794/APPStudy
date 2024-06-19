package com.henry.diagnosisTest.viewMdodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.henry.DialogTest.DialogTestActivity;
import com.henry.diagnosisTest.communication.CommunicationMain;
import com.henry.diagnosisTest.communication.CommunicationModule;
import com.henry.diagnosisTest.communicationImp.ComuniCationBuilerFactory;
import com.henry.diagnosisTest.communicationImp.ModuleBuilder;
import com.henry.diagnosisTest.inter.DiagnosisMainNav;
import com.henry.diagnosisTest.listener.OnTboxDataChangeListener;
import com.henry.diagnosisTest.model.DiagnosisInfo;
import com.henry.diagnosisTest.model.DiagnosisInfoList;
import com.henry.diagnosisTest.model.DiagnosisModule;
import com.henry.diagnosisTest.utils.DDSManager;
import com.quectel.communication.CommunicationBuilder;
import com.quectel.communication.CommunicationBuilderBase;
import com.quectel.communication.CommunicationObservable;
import com.quectel.communication.CommunicationObserver;
import com.quectel.communication.ProgressListener;
import com.quectel.communication.ResponseCallBack;
import com.quectel.communication.model.ResSerializableBean;
import com.quectel.communication.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;

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

    private OnTboxDataChangeListener mOnTboxDataChangeListener;
    private Context mContext;
    private DiagnosisModule mDiagnosisModule;

    //当前类型
    private int currentType = -1;

    private HashMap<String, String> param = new HashMap<>();

    private void initHashMapData() {
        if (param.size() != 0) {
            param.clear();
        }
        if (currentType == 1) {
            param.put("cmd", "diag");
        } else if (currentType == 2) {
            param.put("cmd", "diag");
        }
    }


    /**
     * 初始化Tbox数据变化监听器
     */
    private void initListener() {
        if (null == mOnTboxDataChangeListener) {
            //创建监听器
            mOnTboxDataChangeListener = new OnTboxDataChangeListener() {

                /**
                 * Tbox连接时发送handler消息
                 * @param state
                 */
                @Override
                public void onTboxConnect(boolean state) {
                    Log.d(TAG, "getDiagnosisInfoMain onTboxConnect state = " + state);
                    if (state) {
                        DDSManager.getInstance().setTboxDiagnosticByListener(new Gson().toJson(param));
                    } else {
                        mErrorMsg.postValue("链接异常，请重新诊断！");
                    }
                }

                /**
                 * Tbox数据变化时获取诊断信息
                 * @param data
                 */
                @Override
                public void onTboxDataChange(String data) {
                    Log.d(TAG, "getDiagnosisInfoMain onTboxDataChange currentType = " + currentType);
                    /**
                     * 根据类型获取诊断信息
                     */
                    if (currentType == 1) {
                        getOnlyDiagnosisInfo(mDiagnosisModule, mContext, param);
                    } else if (currentType == 2) {
                        getDiagnosisInfoAll(mContext);
                    }
                }
            };
        }
    }

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
     * 单项诊断
     *
     * @param bean
     * @param context
     */
    public void getDiagnosisInfo(DiagnosisModule bean, Context context) {
        LogUtils.d(TAG, "only");
        getNavigator().showLoading();
        currentType = 1;
        mContext = context;
        mDiagnosisModule = bean;
        initHashMapData();
        initListener();
        //DDSManager.getInstance().addOnTboxDataChangeListenerTest(1,mOnTboxDataChangeListener);
//        DDSManager.getInstance().addOnTboxDataChangeListener(mOnTboxDataChangeListener);
        DDSManager.getInstance().addOnTboxDataChangeListenerTest(1, mOnTboxDataChangeListener);
    }

    private void getOnlyDiagnosisInfo(DiagnosisModule bean, Context mContext, HashMap<String, String> param) {
        CommunicationBuilderBase builder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationMain(bean.getId()), mContext, bean, new Gson().toJson(param));
        CommunicationObservable.getInstance().getObservable(builder,
                new CommunicationObserver(new ResponseCallBack<ResSerializableBean<ArrayList<DiagnosisInfoList>>>() {
                    @Override
                    public void onSuccess(ResSerializableBean<ArrayList<DiagnosisInfoList>> resSerializableBeans) {
                        if (bean.getId() == resSerializableBeans.getId()) {
                            bean.setShowInfo(resSerializableBeans.getCode() == 0);
                            bean.setInfoLists(resSerializableBeans.getData());
                            bean.setStatusCode(0);
                            if (resSerializableBeans.getCode() != 0) {
                                bean.setStatusCode(2);
                            } else {
                                for (DiagnosisInfoList datumDatum : resSerializableBeans.getData()) {
                                    for (DiagnosisInfo diagnosisInfo : datumDatum.getDiagnosisContentList()) {
                                        if (diagnosisInfo.getDiagnosisStatusCode() != 0) {
                                            bean.setStatusCode(1);
                                            datumDatum.setStatusCode(1);
                                        }
                                    }
                                }
                            }
                        }

                        //ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> value = listMutableLiveData.getValue();
                        ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> value = new ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>();
                        value.add(resSerializableBeans);
//                        if (value != null) {
//                            for (int i = 0; i < value.size(); i++) {
//                                if (value.get(i).getId() == resSerializableBeans.getId()) {
//                                    value.get(i).setData(resSerializableBeans.getData());
//                                }
//                            }
//                        }
                        isShowResult.postValue(true);
                        //listMutableLiveData.postValue(listMutableLiveData.getValue());
                        listMutableLiveData.postValue(value);
                    }

                    @Override
                    public void onFault(int code, ResSerializableBean<ArrayList<DiagnosisInfoList>> arrayListResSerializableBean, String errorMsg) {
                        bean.setShowInfo(false);
                        bean.setStatusCode(2);
                        isShowResult.postValue(false);
                        mErrorMsg.postValue(errorMsg);
                        listMutableLiveData.postValue(listMutableLiveData.getValue());
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
    private void getDiagnosisInfoAll(Context context) {
        ArrayList<CommunicationBuilder> communicationBuilders = new ArrayList<>();
        for (DiagnosisModule diagnosisModule : diagnosisModulelist.getValue()) {
            HashMap<String, String> param = new HashMap<>();
            param.put("cmd", "diag");
            CommunicationBuilderBase communicationBuilder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationMain(diagnosisModule.getId()), context, diagnosisModule, new Gson().toJson(param));
            communicationBuilders.add(communicationBuilder);
        }
        //无作用,只是为了数据封装， rxjava 的zip 操作符需要满足是2个数据的前提
        communicationBuilders.add(ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationMain(-1), context, new DiagnosisModule("LOCAL"), null));

        CommunicationObservable.getInstance().getObservable(communicationBuilders, new CommunicationObserver(new ResponseCallBack<ResSerializableBean<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>>() {

            @Override
            public void onSuccess(ResSerializableBean<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>> resSerializableBeanResSerializableBean) {
                ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> data = resSerializableBeanResSerializableBean.getData();
                for (DiagnosisModule diagnosisModule : diagnosisModulelist.getValue()) {
                    for (ResSerializableBean<ArrayList<DiagnosisInfoList>> datum : data) {
                        if (datum.getId() == diagnosisModule.getId()) {
                            diagnosisModule.setShowInfo(datum.getCode() == 0);
                            diagnosisModule.setInfoLists(datum.getData());
                            diagnosisModule.setStatusCode(0);
                            if (datum.getCode() != 0) {
                                diagnosisModule.setStatusCode(2);
                            } else {
                                for (DiagnosisInfoList datumDatum : datum.getData()) {
                                    for (DiagnosisInfo diagnosisInfo : datumDatum.getDiagnosisContentList()) {
                                        if (diagnosisInfo.getDiagnosisStatusCode() != 0) {
                                            diagnosisModule.setStatusCode(1);
                                            datumDatum.setStatusCode(1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                isShowResult.postValue(true);
                listMutableLiveData.postValue(data);
            }

            @Override
            public void onFault(int code, ResSerializableBean<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>> resSerializableBeanResSerializableBean, String errorMsg) {
                for (DiagnosisModule diagnosisModule : diagnosisModulelist.getValue()) {
                    diagnosisModule.setShowInfo(false);
                }
                LogUtils.d(TAG, "onFault resSerializableBeanResSerializableBean = " + resSerializableBeanResSerializableBean);
                isShowResult.postValue(false);
                mErrorMsg.postValue(errorMsg);
                listMutableLiveData.postValue(resSerializableBeanResSerializableBean.getData());
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
