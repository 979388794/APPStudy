package com.henry.jetPackTest.RoomTest.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: henry.xue
 * @date: 2024-03-24
 */

@Entity
public class Student {
    //主键SQL  唯一的autoGenerate自增长
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "pwd")
    private String pwd;

    @ColumnInfo(name = "address")
    private int address;


    public Student(String name, String pwd, int address) {
        this.name = name;
        this.pwd = pwd;
        this.address = address;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", address='" + address + '\'' +
                '}';
    }






}
