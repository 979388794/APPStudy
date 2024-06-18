package com.henry.diagnosisTest.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 日志模块  实现了序列化
 */
public class DiagnosisModule implements Serializable {

    private int id;
    private String moduleName;
    private String moduleIP;
    private String moduleType;
    private long modulePort;
    private boolean isShowInfo;
    private boolean hasHistroy;
    private boolean hasEvents;
    private ArrayList<DiagnosisInfoList> infoLists;


    public DiagnosisModule() {

    }

    public DiagnosisModule(String moduleType) {
        this.moduleType = moduleType;
    }

    /**
     *
     * @param moduleIP   IP
     * @param moduleType 类型
     * @param modulePort 端口
     */
    public DiagnosisModule(String moduleIP, String moduleType, long modulePort){
        this.moduleIP = moduleIP;
        this.moduleType = moduleType;
        this.modulePort = modulePort;
    };

    /**
     * 0 正常   1诊断有问题   2数据获取失败
     */
    private int statusCode = -1;


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleIP() {
        return moduleIP;
    }

    public void setModuleIP(String moduleIP) {
        this.moduleIP = moduleIP;
    }

    public long getModulePort() {
        return modulePort;
    }

    public boolean isShowInfo() {
        return isShowInfo;
    }

    public void setShowInfo(boolean showInfo) {
        isShowInfo = showInfo;
    }

    public void setModulePort(long modulePort) {
        this.modulePort = modulePort;
    }

    public ArrayList<DiagnosisInfoList> getInfoLists() {
        return infoLists;
    }

    public void setInfoLists(ArrayList<DiagnosisInfoList> infoLists) {
        this.infoLists = infoLists;
    }

    public boolean hasHistroy() {
        return hasHistroy;
    }

    public void setHasHistroy(boolean hasHistroy) {
        this.hasHistroy = hasHistroy;
    }

    public boolean hasEvent() {
        return hasEvents;
    }

    public void setHasEvent(boolean hasEvent) {
        this.hasEvents = hasEvent;
    }


    /**
     *
     *诊断模块
     *
     *模块名称
     *模块IP
     *模块类型
     *模块端口
     *有无显示信息
     *有无历史记录
     *有无事件
     *信息集合
     *状态码
     */
    @Override
    public String toString() {
        return "DiagnosisModule{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", moduleIP='" + moduleIP + '\'' +
                ", moduleType='" + moduleType + '\'' +
                ", modulePort=" + modulePort +
                ", isShowInfo=" + isShowInfo +
                ", hasHistroy=" + hasHistroy +
                ", hasEvents=" + hasEvents +
                ", infoLists=" + infoLists +
                ", statusCode=" + statusCode +
                '}';
    }
}
