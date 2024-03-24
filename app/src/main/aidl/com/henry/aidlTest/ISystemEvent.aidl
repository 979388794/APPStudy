package com.henry.aidlTest;

import com.henry.aidlTest.IEventCallback;

interface ISystemEvent {
    void registerCallback(IEventCallback callback);

    void unregisterCallback(IEventCallback callback);

    void sendEvent(int type, String value);
}