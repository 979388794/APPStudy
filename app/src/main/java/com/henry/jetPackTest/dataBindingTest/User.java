package com.henry.jetPackTest.dataBindingTest;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;


/**
 * @author: henry.xue
 * @date: 2024-03-22
 */
public class User extends BaseObservable {
    private String name;
    private String password;


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Bindable   //BR里面标记生成  name数值标记
    public String getName() {
        return name;
    }

    @Bindable    //BR里面标记生成  password数值标记
    public String getPassword() {
        return password;
    }


    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);//APT技术  BR文件
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);//APT技术  BR文件
    }
}
