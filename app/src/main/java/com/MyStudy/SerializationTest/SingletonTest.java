package com.MyStudy.SerializationTest;

import java.io.Serializable;

//Q: -当单例类被多个类加载器加载，如何还能保持单例？
//A: 用多个类加载器的父类来加载单例类

public class SingletonTest {



}

/**
 * 单例类防止反序列化
 */
class Singleton implements Serializable{
    public static Singleton INSTANCE = new Singleton();

    private Singleton(){}

    private Object readResolve(){
        return INSTANCE;
    }
}

/**
 * 单例类如何防止反射
 */
class Singleton1 {

    private static boolean flag = false;

    private Singleton1(){
        synchronized(Singleton.class){
            if(flag == false){
                flag = !flag;
            } else {
                throw new RuntimeException("单例模式被侵犯！");
            }
        }
    }

    private  static class SingletonHolder {
        private static final Singleton1 INSTANCE = new Singleton1();
    }

    public static Singleton1 getInstance(){
        return SingletonHolder.INSTANCE;
    }


}
