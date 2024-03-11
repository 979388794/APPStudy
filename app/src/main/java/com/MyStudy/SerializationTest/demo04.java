package com.MyStudy.SerializationTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 1. 如果不需要保存父类的值，那么没什么问题，只不过序列化会丢失父类的值
 * 2. 如果在子类保存父类的值，则需要在父类提供一个无参构造，不然报错InvalidClassException
 * 子类在序列化的时候需要额外的序列化父类的域（如果有这个需要的话）。那么在反序列的时候，
 * 由于构建User实例的时候需要先调用父类的构造函数，然后才是自己的构造函数。反序列化时，为了构造父对象，只能调用父类的无参构造函数作为默认的父对象,因此当我们取父对象的变量值时，
 * 它的值是调用父类无参构造函数后的值。如果你考虑到这种序列化的情况，在父类无参构造函数中对变量进行初始化。或者在readObject方法中进行赋值。
 * 我们只需要在Person中添加一个空的构造函数即可
 * 3. 自定义序列化过程
 */
public class demo04 {



    static class Person{
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

    static class User extends Person implements Serializable {
        public User(String name, int age,String sex,int id) {
            super(sex,id);
            this.name = name;
            this.age = age;
        }

        public User(){
            super();
        };


        public String name;
        public int age;

        private void writeObject(ObjectOutputStream out) throws IOException {//不是重写父类的方案
            out.defaultWriteObject();
            out.writeObject(getSex());
            out.writeInt(getId());
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            setSex((String)in.readObject());
            setId(in.readInt());
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
        parentNotSerializeTest();
    }

    private static void parentNotSerializeTest() {

        User user = new User("zero", 18,"男",1);
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
            user = (User)ois.readObject();
            System.out.println("反序列化后 2: " + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}