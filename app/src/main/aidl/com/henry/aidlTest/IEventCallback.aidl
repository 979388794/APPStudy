package com.henry.aidlTest;

interface IEventCallback
{
    oneway void onSystemEvent(int type, String value);
}