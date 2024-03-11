package com.MyStudy.aidlTest;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;


public class SystemEventManager {

    private static final String TAG = "xuejie";
    // 系统服务注册时使用的名字, 确保和已有的服务名字不冲突
    public static final String SERVICE = "test_systemevent";
    private final Context mContext;
    private final ISystemEvent mService;

    public SystemEventManager(Context context, ISystemEvent service) {
        mContext = context;
        mService = service;
        Log.d(TAG, "SystemEventManager init");
    }

    public void register(IEventCallback callback) {
        try {
            mService.registerCallback(callback);
        } catch (RemoteException e) {
            Log.w(TAG, "remote exception happen");
            e.printStackTrace();
        }
    }

    public void unregister(IEventCallback callback) {
        try {
            mService.unregisterCallback(callback);
        } catch (RemoteException e) {
            Log.w(TAG, "remote exception happen");
            e.printStackTrace();
        }
    }

    /**
     * Send event to SystemEventService.
     */
    public void sendEvent(int type, String value) {
        try {
            mService.sendEvent(type, value);
        } catch (RemoteException e) {
            Log.w(TAG, "remote exception happen");
            e.printStackTrace();
        }
    }
}
