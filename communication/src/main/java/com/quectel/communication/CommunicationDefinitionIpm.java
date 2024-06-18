package com.quectel.communication;

import com.quectel.communication.model.ResSerializableBean;

import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import io.reactivex.rxjava3.functions.BiFunction;

/**
 * Created by xiexiangyu
 * on 2022/3/30
 */

/**
 * 通信定义真正实现类 实现了通信定义接口的两个方法
 *
 */

public class CommunicationDefinitionIpm<T> implements CommunicationDefinition<T> {

    /**
     * 获取数据
     *
     *根据泛型类型获取实际类型参数，然后使用 Gson 库将输入的字符串数据反序列化为对应的实际类型参数的对象，
     *并封装到一个ResSerializableBean对象中返回。
     *
     * @param data
     * @return
     */
    @Override
    public ResSerializableBean<T> getData(String data) {
        Type types = this.getClass().getGenericSuperclass();
        Objects.requireNonNull(types);
        ParameterizedType parameterized = (ParameterizedType) types;
        Objects.requireNonNull(parameterized);
        Type actualTypeArgument = parameterized.getActualTypeArguments()[0];
        ResSerializableBean resSerializableBean = CommunicationObservable.gson.fromJson(new StringReader(data), actualTypeArgument);
        return resSerializableBean;
    }


    /**
     * 返回空
     * @return
     */
    @Override
    public BiFunction getBiFunction() {
        return null;
    }
}
