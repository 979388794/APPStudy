package com.quectel.communication;

import com.quectel.communication.model.ResSerializableBean;
import com.quectel.communication.util.LogUtils;

import java.net.SocketTimeoutException;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 观察者
 */
public class CommunicationObserver implements Observer<ResSerializableBean> {


    /**
     *  成员变量说明：
     *
     *   responseCallBack：响应事件的回调接口，用于处理响应信息。
     *   progressListener：请求开始时的初始化回调，用于显示进度条或其他进度信息。
     *   disposable：RxJava 中的 Disposable 对象，用于取消订阅以避免内存泄漏。
     */

    /**
     * 响应事件
     */
    private final ResponseCallBack responseCallBack;
    /**
     * 请求开始时的初始化回调
     */
    private final ProgressListener progressListener;
    /**
     * RxJava 订阅前的操作
     */
    private Disposable disposable;


    /**
     * 初始化 callback 和 listener
     *
     * @param responseCallBack
     * @param progressListener
     */

    public CommunicationObserver(ResponseCallBack responseCallBack, ProgressListener progressListener) {
        this.responseCallBack = responseCallBack;
        this.progressListener = progressListener;
    }


    /**
     * 当建立订阅时开始加载
     */
    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        if (progressListener != null) {
            progressListener.startProgress();
        }
    }


    /**
     * 根据消息成功或失败，回调onSuccess和onFault方法。
     *
     * @param resSerializableBean the item emitted by the Observable
     */
    @Override
    public void onNext(ResSerializableBean resSerializableBean) {
        if (resSerializableBean.getCode() == ResponseCode.CODE_SUCCESS) {
            responseCallBack.onSuccess(resSerializableBean);
        } else {
            responseCallBack.onFault(resSerializableBean.getCode(), resSerializableBean, resSerializableBean.getMessage());
        }
    }


    /**
     * 当观察者接收到一个错误事件时会调用此方法。
     * 如果收到的错误是 SocketTimeoutException 类型的，会执行相应的处理，
     * 调用 responseCallBack.onFault() 方法来处理通讯超时的情况。
     * 取消订阅（如果订阅未取消），并取消进度监听器。`
     *
     * @param e the exception encountered by the Observable
     */
    @Override
    public void onError(Throwable e) {
        try {
            //TODO
            if (e instanceof SocketTimeoutException) {
                //连接超时
                responseCallBack.onFault(ResponseCode.CODE_10001, null, "通讯超时，请检查是否连接成功");
            }

        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            LogUtils.e(this, "error:" + e.getMessage());
            if (disposable != null && !disposable.isDisposed()) { //事件完成取消订阅
                disposable.dispose();
            }
            if (progressListener != null) {
                progressListener.cancelProgress();
            }
        }
    }

    /**
     * onComplete() 方法：
     * 当观察者接收到完成事件时会调用此方法。
     * 取消订阅（如果订阅未取消），并取消进度监听器。
     */
    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) { //事件完成取消订阅
            disposable.dispose();
        }
        if (progressListener != null) {
            progressListener.cancelProgress();
        }
    }
}