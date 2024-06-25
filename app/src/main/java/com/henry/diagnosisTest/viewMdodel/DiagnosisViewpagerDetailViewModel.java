package com.henry.diagnosisTest.viewMdodel;



import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import com.henry.diagnosisTest.communication.CommunicationModule;
import com.henry.diagnosisTest.communication.CommunicationViewpagerDetail;
import com.henry.diagnosisTest.communicationImp.ComuniCationBuilerFactory;
import com.henry.diagnosisTest.communicationImp.ModuleBuilder;
import com.henry.diagnosisTest.inter.DiagnosisViewpagerDetailNav;
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

import java.util.ArrayList;
import java.util.HashMap;


public class DiagnosisViewpagerDetailViewModel extends BaseViewModel<DiagnosisViewpagerDetailNav> {
    public MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>> listMutableLiveData = new MutableLiveData<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>();
    public MutableLiveData<ArrayList<DiagnosisModule>> diagnosisModulelist = new MutableLiveData<ArrayList<DiagnosisModule>>();
    public MutableLiveData<Boolean> isShowResult = new MutableLiveData<>(false);
    public MutableLiveData<String> mErrorMsg = new MutableLiveData<>();
    private OnTboxDataChangeListener mOnTboxDataChangeListener;
    private Context mContext;
    private DiagnosisModule mDiagnosisModule;
    private HashMap<String, String> param = new HashMap<>();


    private static String TAG = "DiagnosisViewpagerDetailViewModel";

    public void getDiagnosisModuleList(Context context) {

        CommunicationObservable.getInstance().getObservable(new ModuleBuilder(context, new CommunicationModule()),
                new CommunicationObserver(new ResponseCallBack<ResSerializableBean<ArrayList<DiagnosisModule>>>() {

                    @Override
                    public void onSuccess(ResSerializableBean<ArrayList<DiagnosisModule>> diagnosisModules) {
                        diagnosisModulelist.setValue(diagnosisModules.getData());
                    }

                    @Override
                    public void onFault(int code, ResSerializableBean<ArrayList<DiagnosisModule>> diagnosisModules, String errorMsg) {
                        mErrorMsg.setValue(errorMsg);
                    }
                }, new ProgressListener() {

                    @Override
                    public void startProgress() {
                        getNavigator().showLoading();
                    }

                    @Override
                    public void cancelProgress() {
                        getNavigator().hideLoading();
                    }
                }));
    }

    private void initListener() {
        if (null == mOnTboxDataChangeListener) {
            mOnTboxDataChangeListener = new OnTboxDataChangeListener() {
                @Override
                public void onTboxConnect(boolean state) {
                    Log.d(TAG, "getDiagnosisInfoCycle onTboxConnect state = " + state);
                    if (state) {
                        DDSManager.getInstance().setTboxDiagnosticByListener(new Gson().toJson(param));
                    } else {
                        mErrorMsg.postValue("链接异常，请重新诊断！");
                    }
                }

                @Override
                public void onTboxDataChange(String data) {
                    Log.d(TAG, "getDiagnosisInfoCycle onTboxDataChange");
                    getDiagnosisInfoCycle(mDiagnosisModule, mContext, param);
                }
            };
        }
    }

    //周期 no dialog
    public void getDiagnosisInfo(DiagnosisModule bean, Context context) {
        if (param.size() > 0) {
            param.clear();
        }
        param.put("cmd", "diag");
        mContext = context;
        mDiagnosisModule = bean;
        initListener();
//        DDSManager.getInstance().addOnTboxDataChangeListener(mOnTboxDataChangeListener);
        DDSManager.getInstance().addOnTboxDataChangeListenerTest(1, mOnTboxDataChangeListener);
    }

    private void getDiagnosisInfoCycle(DiagnosisModule bean, Context mContext, HashMap<String, String> param) {
        CommunicationBuilderBase builder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationViewpagerDetail(bean.getId()), mContext, bean, new Gson().toJson(param));
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
    }


    public void getDiagnosisInfo(Context mContext) {
        getNavigator().showLoading();
        /**
         Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        //默认在主线程里执行该方法
        public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
        int pingResult = DeviceUtil.pingNet();
        e.onNext(pingResult);
        e.onComplete();
        }
        });
         **/
        ArrayList<CommunicationBuilder> communicationBuilders = new ArrayList<>();
        for (DiagnosisModule diagnosisModule : diagnosisModulelist.getValue()) {
            HashMap<String, String> param = new HashMap<>();
            param.put("cmd", "diag");
            CommunicationBuilderBase communicationBuilder = ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationViewpagerDetail(diagnosisModule.getId()), mContext, diagnosisModule, new Gson().toJson(param));
            communicationBuilders.add(communicationBuilder);
        }
        //无作用,只是为了数据封装， rxjava 的zip 操作符需要满足是2个数据的前提
        communicationBuilders.add(ComuniCationBuilerFactory.getCommunicationBuilder(new CommunicationViewpagerDetail(-1), mContext, new DiagnosisModule("LOCAL"), null));

        CommunicationObservable.getInstance().getObservable(communicationBuilders, new CommunicationObserver(new ResponseCallBack<ResSerializableBean<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>>>() {

            @Override
            public void onSuccess(ResSerializableBean<ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>>> resSerializableBeanResSerializableBean) {
                ArrayList<ResSerializableBean<ArrayList<DiagnosisInfoList>>> data = resSerializableBeanResSerializableBean.getData();
                for (DiagnosisModule diagnosisModule : diagnosisModulelist.getValue()) {
                    for (ResSerializableBean<ArrayList<DiagnosisInfoList>> datum : data) {
                        if (datum.getId() == diagnosisModule.getId()) {
                            diagnosisModule.setShowInfo(datum.getCode() == 0);
                            diagnosisModule.setInfoLists(datum.getData());
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
                isShowResult.postValue(false);
                mErrorMsg.postValue(errorMsg);
                listMutableLiveData.postValue(resSerializableBeanResSerializableBean.getData());
            }

        }, new ProgressListener() {

            @Override
            public void startProgress() {
                getNavigator().showLoading();
            }

            @Override
            public void cancelProgress() {
                getNavigator().hideLoading();
            }
        }));

    }

    @Override
    public void resetListener() {
        super.resetListener();
        if (null != mOnTboxDataChangeListener) {
            DDSManager.getInstance().removeOnTboxDataChangeListener(mOnTboxDataChangeListener);
            mOnTboxDataChangeListener = null;
        }
    }
}
