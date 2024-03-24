package com.henry.HotFixTest;

import android.app.Application;
import android.widget.Toast;

/**
 * @author: henry.xue
 * @date: 2024-03-05
 */
public class TestClass {
    public void showToast(String str, Application context){
        Toast.makeText(context,"i am bug!"+1/0,Toast.LENGTH_SHORT).show();
    }

}
