package com.henry.basic.reflectTest;

/**
 * @author: henry.xue
 * @date: 2024-06-07
 */

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class MyClass<T> {
    /**
     * 在getClassName方法内部，首先通过getClass().getGenericSuperclass()获取当前类的泛型超类信息。
     * 判断获取到的超类信息是否为参数化类型。
     * 如果是参数化类型，则强转为ParameterizedType型，并通过getActualTypeArguments()[0]获取具体泛型类型参数。
     * 返回该具体类型的类名。
     */
    public String getClassName() {
        Type type = getClass().getGenericSuperclass();

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type actualType = parameterizedType.getActualTypeArguments()[0];
            return actualType.getTypeName();
        }

        return null;
    }

    public static void main(String[] args) {
        MyClass<String> myClass = new MyClass<String>() {};
        System.out.println(myClass.getClassName()); // 输出 java.lang.String
    }
}