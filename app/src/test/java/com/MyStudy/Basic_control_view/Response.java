package com.MyStudy.Basic_control_view;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author: henry.xue
 * @date: 2024-02-27
 */

@RunWith(JUnit4.class)
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

    @Test
    public static void main(String[] args) {
        Response<Data> dataResponse = new Response<>(new Data("数据"), 1, "成功");
        Gson gson = new Gson();
        String json = gson.toJson(dataResponse);
        System.out.println(json);
        //来将对象转换为json字符串
        Response<Data> resp = gson.fromJson(json, new TypeToken<Response<Data>>() {
        }.getType());
        System.out.println(resp.data.result);

        // 调用Gson的 <T> t fromJson(String, Class)方法，将Json串转换为对象
        Response person = gson.fromJson(json, Response.class);
        System.out.println(person);

    }
    //为什么TypeToken要定义为抽象类?
    /**
     *  在进行GSON反序列化时，存在泛型时，可以借助 TypeToken 获取Type以完成泛型的反序列化。但是为什么TypeToken 要被定义为抽象类呢?
     *  因为只有定义为抽象类或者接口，这样在使用时，需要创建对应的实现类，此时确定泛型类型，编译才能够将泛型signature信息记录到Class元数据中。
     */
    @Test
    public void testListToJson()
    {
//        // 先准备一个List集合
//        List<Response> list = new ArrayList<Response>();
//        list.add(new Response(new Data("数据1"), 10, "成功"));
//        list.add(new Response(new Data("数据2"), 20, "成功"));
//        System.out.println(list);
//        // 创建Gson实例
//        Gson gson = new Gson();
//        // 调用Gson的toJson方法
//        String listJson = gson.toJson(list);
//        System.out.println(listJson);
        System.out.println("-----------------");
    }

}
