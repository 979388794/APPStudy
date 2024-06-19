package com.quectel.communication;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.quectel.communication.model.ResSerializableBean;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;


/**
 * 通信建设基类 是一个抽象类  单独实现了通信建设接口
 * <p>
 * 只实现了获取被观察者这个方法
 */
public abstract class CommunicationBuilderBase implements CommunicationBuilder {

    String TAG = getClass().getSimpleName();
    private CommunicationDefinition communicationDefinition;

    public CommunicationBuilderBase(CommunicationDefinition communicationDefinition) {
        this.communicationDefinition = communicationDefinition;
    }

    @Override
    public CommunicationDefinition getCommunicationDefinition() {
        return communicationDefinition;
    }

    /**
     * 使用Rxjava异步框架创建被观察者
     */
    @Override
    public Observable getObservable() {
        return Observable.create(new ObservableOnSubscribe<ResSerializableBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ResSerializableBean> e) throws Exception {
                /**
                 * communicationData :通信数据
                 */
                String communicationData = getCommunicationAction();

                //如果数据为空，返回请求超时
                if (TextUtils.isEmpty(communicationData)) {
                    e.onNext(new ResSerializableBean(ResponseCode.CODE_10001, "请求超时,请稍后再试"));
                } else {
                    //数据不为空，返回ResSerializableBean对象
                    Log.i(TAG, communicationData);
                    /**
                     * getData()返回原始bean对象，实现在CommunicationDefinitionIpm.java
                     */
                    e.onNext(communicationDefinition.getData(communicationData));
                }
                e.onComplete();
            }
        });
    }
}
