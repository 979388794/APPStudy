package com.quectel.communication;

import io.reactivex.rxjava3.core.Observable;



/**
 * 通信建设接口
 * <p>
 * 三个待实现的方法
 * 1.获取被观察者
 * 2.获取通信动作
 * 3.获取通信定义
 *
 * @param <T>
 */
public interface CommunicationBuilder<T> {

    //获取被观察者
    public Observable getObservable();


    //获取源文件
    public String getCommunicationAction() throws Exception;

    //获取
    public CommunicationDefinition getCommunicationDefinition();

}
