package com.henry.JsonTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: henry.xue
 * @date: 2024-03-11
 */
public class FastJsonTest {

    public static class User {

        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class Group {
        public Group() {
        }

        private Long id;
        private String name;
        private List<User> users = new ArrayList<User>();

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

        public void addUser(User user) {
            users.add(user);
        }

        @Override
        public String toString() {
            return "Group{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", users=" + users +
                    '}';
        }
    }


    public static void main(String[] args) {

        //序列化
        Group group = new Group();
        group.setId(0L);
        group.setName("admin");

        User guestUser = new User();
        guestUser.setId(2L);
        guestUser.setName("guest");

        User rootUser = new User();
        rootUser.setId(3L);
        rootUser.setName("root");

        group.addUser(guestUser);
        group.addUser(rootUser);

        String jsonString = JSON.toJSONString(group);
        System.out.println(jsonString);

        //反序列化
        //"{}"表示对象，"[]"表示对象数组（List），一定要有getter和setter。
        String jsonString1 = "{\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"name\":\"guest\"},{\"id\":3,\"name\":\"root\"}]}";
        Group group1 = JSON.parseObject(jsonString1, Group.class);
        System.out.println(group1.getUsers().get(0).toString());
        System.out.println(group1.toString());

        //处理时间
        Date date = new Date(System.currentTimeMillis());
        System.out.println(JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println(JSON.toJSONString(date, SerializerFeature.UseISO8601DateFormat));
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        System.out.println(JSON.toJSONString(date, SerializerFeature.WriteDateUseDateFormat));
    }

}
