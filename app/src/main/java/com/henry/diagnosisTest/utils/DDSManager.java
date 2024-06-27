package com.henry.diagnosisTest.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.henry.diagnosisTest.listener.OnTboxDataChangeListener;
import com.incall.soabridgeadapter.SoaBridgeAdapterManager;
import com.incall.soabridgeadapter.callback.SoaBridgeConnectCallback;
import com.incall.soabridgeadapter.tbox.SoaBridgeTboxClientManager;
import com.quectel.communication.util.LogUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DDSManager {

    private SoaBridgeTboxClientManager mSoaBridgeTboxClientManager = null;
    private SoaBridgeAdapterManager mSoaBridgeAdapterManager = null;
    private SoaBridgeConnectCallback mSoaBridgeConnectCallback = null;
    private SoaBridgeTboxClientManager.OnSoaBridgeTboxClientListener mOnSoaBridgeTboxClientListener = null;
    private String result = null;
    private String mParamMa = null;
    private static Context mContext = null;
    private boolean isFirstedSended = true;
    private static String TAG = "DDSManager";
    private final byte[] bytes = new byte[0];
    private ArrayList<String> aResultList = new ArrayList<>();
    private ArrayList<OnTboxDataChangeListener> aListenerList = new ArrayList<>();
    private String backData = null;
    private LocalHandler mLocalHandler;
    private int count = 1;
    private int current = 0;

    private static class DDSManagerHolder {
        private static DDSManager INSTANCE = new DDSManager();
    }

    public static DDSManager getInstance() {
        return DDSManagerHolder.INSTANCE;
    }

    private DDSManager() {
    }

    public void init(Context context) {
        Log.d(TAG, "app->soa init");
        if (null == mContext) {
            mContext = context;
        }
        mLocalHandler = new LocalHandler(this);
        initSoaBirdge();
    }

    private void initSoaBirdge() {
        Log.d(TAG, "app->soa initSoaBirdge");
        if (null == mSoaBridgeConnectCallback) {
            mSoaBridgeConnectCallback = new SoaBridgeConnectCallback() {
                @Override
                public void onConnectStateChanged(boolean b) {
                    if (b) {
                        initSoaTbox();
                    }
                }
            };
        }

        if (null == mSoaBridgeAdapterManager) {
            mSoaBridgeAdapterManager = SoaBridgeAdapterManager.getInstance(mContext, mSoaBridgeConnectCallback);
        }
    }

    private void initSoaTbox() {
        Log.d(TAG, "app->soa initSoaTbox");
        if (null == mOnSoaBridgeTboxClientListener) {
            mOnSoaBridgeTboxClientListener = new SoaBridgeTboxClientManager.OnSoaBridgeTboxClientListener() {
                @Override
                public void onRadioTechEvent(int state) {
                    onTboxConnectEvent("soa->app onRadioTechEvent");
                }

                @Override
                public void onSignalStrengthEvent(int state) {
                    onTboxConnectEvent("soa->app onSignalStrengthEvent");
                }

                @Override
                public void onTboxDiagnosticEvent(String state) {
                    Log.d(TAG, "soa->app onTboxDiagnosticEvent = " + state);
                    result = state;
                    aResultList.add(result);
                    synchronized (bytes) {
                        notifyListenerTboxDataChange();
                    }
                }
            };
        }

        if (null == mSoaBridgeTboxClientManager) {
            mSoaBridgeTboxClientManager = (SoaBridgeTboxClientManager) mSoaBridgeAdapterManager
                    .getSoaBridgeAdapterManager(SoaBridgeAdapterManager.SOA_BRIDGE_TBOX_SERVICE);
        }
        mSoaBridgeTboxClientManager.addSoaBridgeTboxListener(mOnSoaBridgeTboxClientListener);
    }

    private void onTboxConnectEvent(String tag) {
        Log.d(TAG, "soa->app onTboxConnectEvent tag = " + tag + ",,isFirstedSended = " + isFirstedSended);
        if (isFirstedSended) {
            synchronized (bytes) {
                isFirstedSended = false;
                if (aListenerList.size() > 0) {
                    for (OnTboxDataChangeListener listener : aListenerList) {
                        if (null != listener) {
                            listener.onTboxConnect(true);
                        }
                    }
                }
            }
        }
    }

    //先添加诊断回调，再下发诊断指令
    public void addOnTboxDataChangeListener(OnTboxDataChangeListener listener) {
        Log.d(TAG, "addOnTboxDataChangeListener listener = " + listener + ",isFirstedSended = " + isFirstedSended);
        if (aListenerList.size() != 0 || aResultList.size() != 0) {
            aListenerList.clear();
            aResultList.clear();
        }
        if (null != listener) {
            synchronized (bytes) {
                aListenerList.add(listener);
                if (!isFirstedSended) {
                    listener.onTboxConnect(true);
                }
                Log.d(TAG, "addOnTboxDataChangeListener over");
            }
        }
    }

    private static String getStringFromAssets(String assetsFile){
        AssetManager assetManager = mContext.getAssets();
        InputStream inputStream = null;
        BufferedReader bufferedReader=null;
        InputStreamReader inputStreamReader=null;
        try {
            inputStream = assetManager.open(assetsFile);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //先添加诊断回调，再下发诊断指令
    /*public void addOnTboxDataChangeListenerTest(int type ,OnTboxDataChangeListener listener){
        Log.d(TAG,"addOnTboxDataChangeListener type = " + type + ",listener = " + listener + ",isFirstedSended = " + isFirstedSended);
        getTboxDataTest(type);
        if(null != backData){
            mLocalHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onTboxDataChange(backData);
                }
            },2000L);
            return;
        }
        if (null != listener){
            synchronized (bytes){
                aListenerList.add(listener);
                if (!isFirstedSended){
                    listener.onTboxConnect(true);
                }
            }
        }
    }*/

    //先添加诊断回调，再下发诊断指令
    public void addOnTboxDataChangeListenerTest(int type,OnTboxDataChangeListener listener){
        LogUtils.d(TAG,"addOnTboxDataChangeListenerTest listener = " +listener    +",type = "+ type);
        aListenerList.clear();
        if(type == 1){
            backData = getStringFromAssets("oneClickData.json");
        }else if(type == 2){
            backData = getStringFromAssets("eventData.json");
        }else if (type==3){
            backData = getStringFromAssets("hisData.json");
        }else if (type==4){
            backData = getStringFromAssets("oneClickData.json");
        }else if (type==5){
            backData = getStringFromAssets("oneClickData.json");
        }
        LogUtils.d(TAG,"addOnTboxDataChangeListenerTest listener = "+ listener +   ",type = "  +  type +   ",,backData = " +   backData);
        aListenerList.add(listener);
        listener.onTboxConnect(true);
    }

//    public void addOnTboxDataChangeListenerTest(int type, OnTboxDataChangeListener listener) {
//        Log.d(TAG, "addOnTboxDataChangeListenerTest listener = " + listener + ",type = " + type);
//        aListenerList.clear();
//        if (type == 1) {
//            backData = main_all_3;
//        } else if (type == 2) {
//            backData = event_data_test_1;
//        }
//        Log.d(TAG, "addOnTboxDataChangeListenerTest listener = " + listener + ",type = " + type + ",,backData = " + backData);
//        aListenerList.add(listener);
//        listener.onTboxConnect(true);
//    }

    //可以不使用，通过回调信息集合删除回调
    public void removeOnTboxDataChangeListener(OnTboxDataChangeListener listener) {
        Log.d(TAG, "removeOnTboxDataChangeListener listener = " + listener);
        if (null != listener) {
            synchronized (bytes) {
                aListenerList.remove(listener);
                if (aListenerList.size() == 0) {
                    aResultList.clear();
                }
            }
        }
    }

    //先添加诊断回调，再下发诊断指令
    public void setTboxDiagnosticByListener(String paramMa) {
        //todo test
        if (null != aListenerList) {
            Log.d(TAG, "paramMa = " + paramMa);
            Log.d(TAG, "setTboxDiagnosticByListener aListenerList = " + aListenerList);
            for (OnTboxDataChangeListener onTboxDataChangeListener : aListenerList) {
                onTboxDataChangeListener.onTboxDataChange(backData);
            }
            return;
        }
        //todo test end
        if (TextUtils.isEmpty(paramMa)) {
            Log.d(TAG, "app->soa setTboxDiagnosticByListener null");
            return;
        }
        Message message = new Message();
        message.what = count;
        message.obj = paramMa;
        mLocalHandler.sendMessageDelayed(message, 100L);
        Log.d(TAG, "app->soa setTboxDiagnosticByListener : " + mParamMa + ",,,count = " + count);
        count++;
    }


    private void setTboxDiagnosticByManager(String paramMa) {
        if (TextUtils.isEmpty(paramMa)) {
            Log.d(TAG, "app->soa setTboxDiagnosticByManager null");
            return;
        }
        mParamMa = paramMa;
        Log.d(TAG, "app->soa setTboxDiagnosticByManager : " + mParamMa + "..mSoaBridgeTboxClientManager = " + mSoaBridgeTboxClientManager);
        if (null == mSoaBridgeTboxClientManager) {
            initSoaBirdge();
        }
        mSoaBridgeTboxClientManager.setTboxDiagnostic(new int[]{0x1000, 0}, paramMa);
    }

    //
    private void notifyListenerTboxDataChange() {
        //根据listener result ,list index返回对应数据
        Log.d(TAG, "getTboxData aListenerList.size() = " + aListenerList.size() + ",aResultList.size() = " + aResultList.size());
        if (aListenerList.size() == 0) {
            aResultList.clear();
            return;
        }
        //基于一一对应原则
        if (aListenerList.size() == aResultList.size()) {
            for (int i = 0; i < aListenerList.size(); i++) {
                OnTboxDataChangeListener listener = aListenerList.get(i);
                backData = aResultList.get(i);
                if (null != listener) {
                    listener.onTboxDataChange(backData);
                }
            }
            aResultList.clear();
        } else {
            Log.e(TAG, "lisner error");
            for (OnTboxDataChangeListener listener : aListenerList) {
                Log.e(TAG, "aListenerList = " + listener);
                listener.onTboxConnect(false);
            }
            for (String data : aResultList) {
                Log.e(TAG, "aResultList = " + data);
            }
            aListenerList.clear();
            aResultList.clear();
        }
        Log.d(TAG, "no listener for back data!");
    }

    private void notifyListenerTboxDataChangeOld() {
        //根据listener result ,list index返回对应数据
        Log.d(TAG, "getTboxData aListenerList.size() = " + aListenerList.size() + ",aResultList.size() = " + aResultList.size());
        if (aListenerList.size() == 0) {
            aResultList.clear();
            return;
        }
        //基于回调+指令的次序，返回结果，删除之前的回调，保留当前的回调。
        if (aListenerList.size() >= aResultList.size()) {
            for (int i = 0; i < aListenerList.size(); i++) {
                if (i == aResultList.size() - 1) {
                    OnTboxDataChangeListener listener = aListenerList.get(i);
                    backData = aResultList.get(i);
                    if (null != listener) {
                        listener.onTboxDataChange(backData);
                    }
                } else if (i < aResultList.size() - 1) {
                    aListenerList.remove(i);
                    aResultList.remove(i);
                    i--;
                }
            }
            return;
        }
        Log.d(TAG, "no listener for back data!");
    }

    public String getTboxData() {
        Log.d(TAG, "getTboxData for back data!");
        return backData;
    }

    public void reset() {
        Log.d(TAG, "reset");
        if (null != mSoaBridgeTboxClientManager && null != mOnSoaBridgeTboxClientListener) {
            mSoaBridgeTboxClientManager.removeSoaBridgeTboxListener(mOnSoaBridgeTboxClientListener);
        }
        mOnSoaBridgeTboxClientListener = null;
        mSoaBridgeConnectCallback = null;
        mSoaBridgeTboxClientManager = null;
        mSoaBridgeAdapterManager = null;
        count = 1;
        current = 0;
        init(mContext);
    }

    private static class LocalHandler extends Handler {
        private DDSManager ddsManager;

        private LocalHandler(DDSManager manager) {
            ddsManager = new WeakReference<>(manager).get();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (null == ddsManager) {
                Log.d(TAG, "ddsmanger is null");
                return;
            }
            Log.d(TAG, "current is  = " + ddsManager.current + ",,, what = " + msg.what);
            if (msg.what != ddsManager.current && (msg.what - ddsManager.current == 1)) {
                ddsManager.current = msg.what;
                ddsManager.setTboxDiagnosticByManager(String.valueOf(msg.obj));
            } else {
                //异常状态了
                ddsManager.reset();
            }
        }
    }


    private static String main_all_3 = "{\n" +
            " \t\"code\":\t0,\n" +
            " \t\"message\":\t\"success\",\n" +
            " \t\"diag_id\":\t\"20240202104542\",\n" +
            " \t\"location\":\t{\n" +
            " \t\t\"latitude\":\t0,\n" +
            " \t\t\"longitude\":\t0,\n" +
            " \t\t\"altitude\":\t0\n" +
            " \t},\n" +
            " \t\"data\":\t[{\n" +
            " \t\t\t\"diagnosisName\":\t\"外部网络环境\",\n" +
            " \t\t\t\"diag_list\":\t[{\n" +
            " \t\t\t\t\t\"Name\":\t\"附近基站\",\n" +
            " \t\t\t\t\t\"ID\":\t11,\n" +
            " \t\t\t\t\t\"Info\":\t\"LTE:\\n\\tCID=82038792,PLMN=0x64 0xf0 0x10,TAC=39444,PCI=59,EARFCN=100,RSSI=-54,MCC=460,MNC=01\\n\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"信号强度\",\n" +
            " \t\t\t\t\t\"ID\":\t12,\n" +
            " \t\t\t\t\t\"Info\":\t\"LTE:\\tRSSI=-54,RSRQ=-5,RSRP=-78,SNR=274\\n\\tLEVEL=4\\n\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"IMS注册状态\",\n" +
            " \t\t\t\t\t\"ID\":\t13,\n" +
            " \t\t\t\t\t\"Info\":\t\"IMS 激活：未激活, IMS 注册状态：未注册\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"ims状态暂时不支持获取\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"驻网状态\",\n" +
            " \t\t\t\t\t\"ID\":\t14,\n" +
            " \t\t\t\t\t\"Info\":\t\"Data注网信息:  网络制式 LTE, 注册状态 全服务,                             移动设备国家代码 460, 移动设备网络代码 01 \\nvoice 注网信息:  网络制式 LTE, 注册状态 全服务,                             移动设备国家代码 460, 移动设备网络代码 01 \\n\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}]\n" +
            " \t\t}, {\n" +
            " \t\t\t\"diagnosisName\":\t\"SIM\",\n" +
            " \t\t\t\"diag_list\":\t[{\n" +
            " \t\t\t\t\t\"Name\":\t\"SIM卡状态\",\n" +
            " \t\t\t\t\t\"ID\":\t2,\n" +
            " \t\t\t\t\t\"Info\":\t\"SIM读取成功，IMSI: 460096519705047 ICCID: 89860923790041120388 \",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"SIM卡配置\",\n" +
            " \t\t\t\t\t\"ID\":\t3,\n" +
            " \t\t\t\t\t\"Info\":\t\"cqsda01.5gsc.cdiot\\ncqsda03.5gsc.cdiot\\n\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"SIM卡激活\",\n" +
            " \t\t\t\t\t\"ID\":\t4,\n" +
            " \t\t\t\t\t\"Info\":\t\"deny reason : 0 \",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}]\n" +
            " \t\t}, {\n" +
            " \t\t\t\"diagnosisName\":\t\"modem\",\n" +
            " \t\t\t\"diag_list\":\t[{\n" +
            " \t\t\t\t\t\"Name\":\t\"UE功能\",\n" +
            " \t\t\t\t\t\"ID\":\t1,\n" +
            " \t\t\t\t\t\"Info\":\t\"飞行模式关 2，\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"MODEM状态\",\n" +
            " \t\t\t\t\t\"ID\":\t17,\n" +
            " \t\t\t\t\t\"Info\":\t\"MODEM ONLINE\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"MCU启动原因\",\n" +
            " \t\t\t\t\t\"ID\":\t18,\n" +
            " \t\t\t\t\t\"Info\":\t\"启动原因 :RESET_N 引脚复位，AT或API重启过模块\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"拨号状态\",\n" +
            " \t\t\t\t\t\"ID\":\t9,\n" +
            " \t\t\t\t\t\"Info\":\t\"call_id : 1\\ncall_status : CONNECTED\\t\\tdevice : rmnet_data5\\t\\tip4 ip : 10.0.1.21\\t\\tip4 netmask : 255.255.255.252\\t\\tip4 subnet_bits : 30\\t\\tip4 gateway : 10.0.1.22\\t\\tip4_dnsp : 172.17.2.224\\t\\tip4_dnss : 100.100.2.136\\t\\tping pre-tusys.changan.com.cn : succeed, average delay : 59.52 ms\\terror_code : 0\\ncall_id : 3\\ncall_status : CONNECTED\\t\\tdevice : rmnet_data7\\t\\tip4 ip : 10.55.146.35\\t\\tip4 netmask : 255.255.255.248\\t\\tip4 subnet_bits : 29\\t\\tip4 gateway : 10.55.146.36\\t\\tip4_dnsp : 221.7.92.100\\t\\tip4_dnss : 221.5.203.100\\t\\tping cdn.sda.changan.com.cn : succeed, average delay : 40.83 ms\\terror_code : 0\\n\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}]\n" +
            " \t\t}, {\n" +
            " \t\t\t\"diagnosisName\":\t\"tbox\",\n" +
            " \t\t\t\"diag_list\":\t[{\n" +
            " \t\t\t\t\t\"Name\":\t\"子模块网络状态-net_diag_submod_client\",\n" +
            " \t\t\t\t\t\"ID\":\t19,\n" +
            " \t\t\t\t\t\"Info\":\t\"子模块未连接\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"子模块最后网络正常时间-net_diag_submod_client\",\n" +
            " \t\t\t\t\t\"ID\":\t20,\n" +
            " \t\t\t\t\t\"Info\":\t\"\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"子模块网络状态-net_diag_submod_client1\",\n" +
            " \t\t\t\t\t\"ID\":\t19,\n" +
            " \t\t\t\t\t\"Info\":\t\"子模块未连接\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"子模块最后网络正常时间-net_diag_submod_client1\",\n" +
            " \t\t\t\t\t\"ID\":\t20,\n" +
            " \t\t\t\t\t\"Info\":\t\"\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}]\n" +
            " \t\t}, {\n" +
            " \t\t\t\"diagnosisName\":\t\"证书诊断\",\n" +
            " \t\t\t\"diag_list\":\t[{\n" +
            " \t\t\t\t\t\"Name\":\t\"5G证书是否存在\",\n" +
            " \t\t\t\t\t\"ID\":\t15,\n" +
            " \t\t\t\t\t\"Info\":\t\"4G目前不支持\\n\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"5G证书是否有效\",\n" +
            " \t\t\t\t\t\"ID\":\t16,\n" +
            " \t\t\t\t\t\"Info\":\t\"4G目前不支持\\n\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}]\n" +
            " \t\t}, {\n" +
            " \t\t\t\"diagnosisName\":\t\"辅助信息\",\n" +
            " \t\t\t\"diag_list\":\t[{\n" +
            " \t\t\t\t\t\"Name\":\t\"找网模式\",\n" +
            " \t\t\t\t\t\"ID\":\t5,\n" +
            " \t\t\t\t\t\"Info\":\t\"自动找网模式 \",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"运营商\",\n" +
            " \t\t\t\t\t\"ID\":\t6,\n" +
            " \t\t\t\t\t\"Info\":\t\"运营商名称全称 CHN-UNICOM, 运营商名称简称 UNICOM, 移动设备国家代码 460, 移动设备网络代码 01\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"网络制式\",\n" +
            " \t\t\t\t\t\"ID\":\t7,\n" +
            " \t\t\t\t\t\"Info\":\t\"网络制式首选项=N/A, 漫游通知=开启漫游通知\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}, {\n" +
            " \t\t\t\t\t\"Name\":\t\"小区信息\",\n" +
            " \t\t\t\t\t\"ID\":\t8,\n" +
            " \t\t\t\t\t\"Info\":\t\"LTE网络小区信息:\\n\\t小区标识(CID)=82038792,公共陆地移动网(PLMN)=0x64 0xf0 0x10,跟踪区域代码(TAC)=39444,物理小区标识(PCI)=59,绝对无线频率信道号(EARFCN)=100,接收信号强度指示(RSSI)=-52,移动信号国家码(MCC)=460,移动网络代码(MNC)=01\\n\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}]\n" +
            " \t\t}, {\n" +
            " \t\t\t\"diagnosisName\":\t\"应用\",\n" +
            " \t\t\t\"diag_list\":\t[{\n" +
            " \t\t\t\t\t\"Name\":\t\"路由信息\",\n" +
            " \t\t\t\t\t\"ID\":\t10,\n" +
            " \t\t\t\t\t\"Info\":\t\"default via 10.55.146.36 dev rmnet_data7 \\n\",\n" +
            " \t\t\t\t\t\"StatusCode\":\t0,\n" +
            " \t\t\t\t\t\"Suggestion\":\t\"\"\n" +
            " \t\t\t\t}]\n" +
            " \t\t}]\n" +
            " }";


    private static String event_data_test_1 = "{\n" +
            " \t\"data\":\t[{\n" +
            " \t\t\t\"events\":\t[{\n" +
            " \t\t\t\t\t\"date\":\t\"20240222\"\n" +
            " \t\t\t\t}]\n" +
            " \t\t}],\n" +
            " \t\"code\":\t0,\n" +
            " \t\"message\":\t\"success\"\n" +
            " }";


}
