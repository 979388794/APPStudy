package com.MyStudy.SerializationTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 瞬态transient的作用
 */
public class demo02 {

    static class User implements Serializable {

        public User() {
            System.out.println("=============");
        }

        public User(String name, int age) {
            System.out.println("==============");
            this.name = name;
            this.age = age;
        }

        public String name;
        public int age;

        public transient String nickName;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", nickName=" + nickName +
                    '}';
        }
    }

    public static String basePath = System.getProperty("user.dir") + "\\";
    public static String tmp = "D:\\henryTest\\";

    public static void main(String[] args) {
        trasientTest();
    }

    private static void trasientTest() {
        User user = new User("zero", 18);
        user.nickName = "Zero老师";
        System.out.println("1: " + user);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        byte[] userData = null;
        try {
            oos = new ObjectOutputStream(out);
            oos.writeObject(user);
            userData = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(userData));
            user = (User) ois.readObject();
            System.out.println("反序列化后 2: " + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}