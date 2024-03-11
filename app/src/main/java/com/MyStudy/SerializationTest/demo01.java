package com.MyStudy.SerializationTest;

import java.io.Serializable;

/**
 * @author: henry.xue
 * @date: 2024-03-08
 */

/**
 * serialVersionUID的理解
 */
public class demo01 {

    static class User implements Serializable {
        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String name;
        public int age;

//        public String nickName;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
//                    ", nickName=" + nickName +
                    '}';
        }
    }

    static class User1 implements Serializable {

        private static final long serialVersionUID = 2;

        public User1(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String name;
        public int age;
        public String nickName;

        @Override
        public String toString() {
            return "User1{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", nickName=" + nickName +
                    '}';
        }
    }
    public static String basePath = System.getProperty("user.dir") + "\\";
    public static String tmp = "D:\\henryTest\\";

    public static void main(String[] args) {
        NoSerialIdTest();
    //    HasSerialIdTest();
    }

    private static void NoSerialIdTest() {
        User user = new User("zero", 20);
        SerializeableUtils.saveObject(user,tmp+"a.out");
        System.out.println("1: " + user);
        user = SerializeableUtils.readObject(tmp + "a.out");
        System.out.println("反序列化： 2: " + user);
    }

    private static void HasSerialIdTest() {
        User1 user = new User1("zero", 18);
//        SerializeableUtils.saveObject(user,tmp+"b.out");
//        System.out.println("1: " + user);
        user = SerializeableUtils.readObject(tmp + "b.out");
        System.out.println("2: " + user);
    }
}


