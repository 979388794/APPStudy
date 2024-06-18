package com.quectel.communication;


/**
 * 响应回调
 */
public interface ResponseCallBack<T> {
    /**
     * 请求成功
     *
     * @param t        响应内容
     */
    void onSuccess(T t);

    /**
     * 请求失败
     *
     * @param code     错误码
     * @param t        响应内容
     * @param errorMsg 错误信息
     */
    void onFault(int code, T t, String errorMsg);
}