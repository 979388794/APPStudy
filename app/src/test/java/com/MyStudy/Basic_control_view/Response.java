package com.MyStudy.Basic_control_view;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: henry.xue
 * @date: 2024-02-27
 */

public class Response<T> {
    public Response(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    T data;
    int code;
    String message;

    @NonNull
    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ",  code=" + code +
                ", message=' " + message + '\'' +
                '}';
    }

    static class Data {
        public Data(String result) {
            this.result = result;
        }

        String result;

        @Override
        public String toString() {
            return "Data{" +
                    "result='" + result + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        GsonBean();
        GsonList();
        GsonMap();
    }


    /**
     * 先创建Person对象。
     * 在创建Gson对象。
     * 调用Gson的String toJson(Object)方法，来将对象转换为json字符串。
     * 调用Gson的T fromJson()方法，来将json字符串转换为对象。
     */
    static void GsonBean() {
        Response<Data> dataResponse = new Response<>(new Data("Bean数据"), 1, "成功");
        Gson gson = new Gson();
        String json = gson.toJson(dataResponse);
        System.out.println("Bean数据 json字符串 =" + json);
        //来将对象转换为json字符串
        Response<Data> resp = gson.fromJson(json, new TypeToken<Response<Data>>() {
        }.getType());
        System.out.println("Bean数据捕获 data =" + resp);
        // 调用Gson的 <T> t fromJson(String, Class)方法，将Json串转换为对象
        Response resp2 = gson.fromJson(json, Response.class);
        System.out.println("Bean数据 =" + resp2);
        System.out.println("-----------------");
    }

    //为什么TypeToken要定义为抽象类?

    /**
     * 在进行GSON反序列化时，存在泛型时，可以借助 TypeToken 获取Type以完成泛型的反序列化。但是为什么TypeToken 要被定义为抽象类呢?
     * 因为只有定义为抽象类或者接口，这样在使用时，需要创建对应的实现类，此时确定泛型类型，编译才能够将泛型signature信息记录到Class元数据中。
     */
    public static void GsonList() {
        // 先准备一个List集合
        List<Response> list = new ArrayList<Response>();
        list.add(new Response(new Data("List数据1"), 10, "成功"));
        list.add(new Response(new Data("List数据2"), 20, "成功"));
        // 创建Gson实例
        Gson gson = new Gson();
        // 调用Gson的toJson方法
        String listJson = gson.toJson(list);
        System.out.println("list数据 json字符串 =" + listJson);
        // 如果还调用 <T> t fromJson(String, Class)方法，那么返回的虽然还是个List集合，但是集合里面的数据却不是Person对象，而是Map对象
        List fromJson = gson.fromJson(listJson, List.class);
        System.out.println("list数据 fromJson 集合数据类型 =" + fromJson.get(0).getClass());
        // class com.google.gson.internal.LinkedTreeMap
        //要想获取的List还和之前的一毛一样,通过Gson包提供的TypeToken获取
        // 调用Gson的 T fromJson(String, Type)将List集合的json串反序列化为List对象
        List<Response> plist = gson.fromJson(listJson, new TypeToken<List<Response>>() {
        }.getType());
        System.out.println("list数据 fromJson TypeToken =" + plist);
        System.out.println("-----------------");
    }

    /**
     * 转换Map的步骤和转换List的步骤一模一样
     */
    public static void GsonMap() {

        Map<String, Response> map = new HashMap<>();
        map.put("p1", new Response(new Data("Map数据1"), 10, "成功"));
        map.put("p2", new Response(new Data("Map数据2"), 20, "成功"));

        Gson gson = new Gson();
        String mapJson = gson.toJson(map);

        System.out.println("Map数据 json字符串 =" + mapJson);
        Map<String, Response> jsonMap = gson.fromJson(mapJson, new TypeToken<Map<String, Response>>() {
        }.getType());
        System.out.println("Map数据 fromJson TypeToken =" + jsonMap);
    }


}
