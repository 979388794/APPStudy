package com.quectel.communication.model;

import java.io.Serializable;

public class ResSerializableBean<T> implements Serializable {

    /**
     * 可序列化的资源bean对象
     *
     *状态码
     *信息
     *id
     *提示框id
     *是不是压缩文件
     *定位
     *数据
     *
     */

    /**
     * 请求返回码
     */
    private int code;
    /**
     * 请求是否成功提示信息
     */
    private String message;

    /**
     * 标识数据源
     */
    private int id;

    private String diag_id;

    private boolean isZip;

    private Location location;

    private T data;

    public ResSerializableBean() {
    }

    public ResSerializableBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResSerializableBean(int code, String message, int id) {
        this.code = code;
        this.message = message;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isZip() {
        return isZip;
    }

    public void setZip(boolean zip) {
        isZip = zip;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDiag_id(String diag_id) {
        this.diag_id = diag_id;
    }

    public String getDiag_id() {
        return diag_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResSerializableBean{" +
                " code=" + code +
                ",id=" + id +
                ",data = " + data +
                ", message='" + message + '\'' +
                '}';
    }


}
