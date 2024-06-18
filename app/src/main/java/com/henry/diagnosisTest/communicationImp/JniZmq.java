package com.henry.diagnosisTest.communicationImp;


/**
 * jni zmq 的java封装
 */
public class JniZmq {
    public static JniZmq zmq;

    public static JniZmq getInstance(){
        if(zmq == null) {
            zmq = new JniZmq();
        }
        return zmq;
    };

    //todo  暂时省略
//    public native void init(String client_name, String server_name, String address);
//    public native String send(String cmd, int length);
//    public native void destory();
}