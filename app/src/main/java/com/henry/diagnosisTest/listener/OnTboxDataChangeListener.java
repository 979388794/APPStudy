package com.henry.diagnosisTest.listener;

/**
 * Tbox数据变更监听器
 *
 */
public interface OnTboxDataChangeListener {

    /**
     * Tbox连接时
     *
     * Tbox数据变化时
     */
    void onTboxConnect(boolean state);

    void onTboxDataChange(String data);

}
