package com.quectel.communication;


/**
 * 进度监听器
 *
 */
public interface ProgressListener {
    /**
     * 开始加载
     */
    void startProgress();

    /**
     * 取消加载
     */
    void cancelProgress();
}
