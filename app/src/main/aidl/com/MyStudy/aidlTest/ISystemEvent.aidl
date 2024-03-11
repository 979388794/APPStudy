package com.MyStudy.aidlTest;

import com.MyStudy.aidlTest.IEventCallback;

interface ISystemEvent {
    void registerCallback(IEventCallback callback);

    void unregisterCallback(IEventCallback callback);

    void sendEvent(int type, String value);
}