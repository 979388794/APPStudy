package com.henry.JsonTest;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author: henry.xue
 * @date: 2024-03-12
 */
public class JackSonTest {

    public static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }


        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }


    }

    public static void testJackson() {
        try {
            // 创建 ObjectMapper 对象
            ObjectMapper objectMapper = new ObjectMapper();

            // 创建一个 User 对象
            User user = new User("Bob", 30);

            // 将 User 对象转换为 JSON 字符串
            String json = objectMapper.writeValueAsString(user);
            Log.d("JacksonTest", "Serialized JSON: " + json);

            // 将 JSON 字符串转换为 User 对象
            User deserializedUser = objectMapper.readValue(json, User.class);
            Log.d("JacksonTest", "Deserialized User: " + deserializedUser.getName() + ", " + deserializedUser.getAge());
        } catch (Exception e) {
            Log.e("JacksonTest", "Error during Jackson test: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
            testJackson();
    }
}
