package com.MyStudy.aidlTest;

interface IEventCallback
{
    oneway void onSystemEvent(int type, String value);
}