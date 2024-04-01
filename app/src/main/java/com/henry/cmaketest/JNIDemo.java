package com.henry.cmaketest;

/**
 * @author: henry.xue
 * @date: 2024-04-01
 */
public class JNIDemo {



    public JNIDemo() {

    }

    public native String stringJni();
    public native float floatJni(int number,Boolean enabled);
}
