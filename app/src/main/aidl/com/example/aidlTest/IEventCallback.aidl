package com.example.aidlTest;

interface IEventCallback
{
    oneway void onSystemEvent(int type, String value);
}