package com.example.aidlTest;

import com.example.aidlTest.IEventCallback;

interface ISystemEvent {
    void registerCallback(IEventCallback callback);

    void unregisterCallback(IEventCallback callback);

    void sendEvent(int type, String value);
}