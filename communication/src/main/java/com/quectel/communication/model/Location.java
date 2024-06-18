package com.quectel.communication.model;

import java.io.Serializable;

/**
 * 位置信息
 */
public class Location implements Serializable {

    private Double latitude; //纬度
    private Double longitude; //经度
    private Double altitude; //高度

    public Double getLatitude() {//纬度
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {//经度
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() { //高度
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }
}
