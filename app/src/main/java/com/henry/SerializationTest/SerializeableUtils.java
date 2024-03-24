package com.henry.SerializationTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeableUtils {

    public static  <T> byte[] serialize(T t) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(t);
        return out.toByteArray();
    }

    public static <T> T deserialize(byte[] bytes)throws Exception{
        //TODO:
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        T t = (T)ois.readObject();
        return t;
    }

    /**
     *
     * @param obj
     * @param path
     * @return
     */
    synchronized public static boolean saveObject(Object obj, String path) {//持久化
        if (obj == null) {
            return false;
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(path));// 创建序列化流对象
            oos.writeObject(obj);
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close(); // 释放资源
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 反序列化对象
     *
     * @param path
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked ")
    synchronized public static <T> T readObject(String path) {
        ObjectInputStream ojs = null;
        try {
            ojs = new ObjectInputStream(new FileInputStream(path));// 创建反序列化对象
            return (T) ojs.readObject();// 还原对象
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(ojs!=null){
                try {
                    ojs.close();// 释放资源
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
