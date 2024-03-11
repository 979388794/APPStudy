package com.MyStudy.SerializationTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 父类可序列化，如何阻止子类可序列化
 * 1. transient？
 * 2. 实现writeObject，readObject，readObjectNoData 在这些方法里面抛出运行时异常
 */
public class demo05 {

    static class Person implements Serializable {
        private static final long serialVersionUID = 5850510148907441688L;
        private String sex;
        private int id;

        public Person() {
        }

        public Person(String sex, int id) {
            this.sex = sex;
            this.id = id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "sex='" + sex + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    static class User extends Person {
        public User(String name, int age, String sex, int id) {
            super(sex, id);
            this.name = name;
            this.age = age;

        }

        public String name;
        public int age;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    "} " + super.toString();
        }
    }

    static class User1 extends Person {
        public User1(String name, int age, String sex, int id) {
            super(sex, id);
            this.name = name;
            this.age = age;
        }

        public String name;
        public int age;

        private void writeObject(ObjectOutputStream out) throws IOException {
            throw new NotSerializableException("Can not serialize this class");
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            throw new NotSerializableException("Can not serialize this class");
        }

        private void readObjectNoData() throws ObjectStreamException {
            throw new NotSerializableException("Can not serialize this class");
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    "} " + super.toString();
        }
    }

    public static String basePath = System.getProperty("user.dir") + "\\";
    public static String tmp = "D:\\henryTest\\";

    public static void main(String[] args) {
//        ChildNotSerializeTest();
        ChildNotSerializeTest1();
    }

    private static void ChildNotSerializeTest() {

        User user = new User("zero", 18, "男", 1);
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

    private static void ChildNotSerializeTest1() {

        User1 user = new User1("zero", 18, "男", 1);
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
            user = (User1) ois.readObject();
            System.out.println("反序列化后 2: " + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}