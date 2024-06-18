package com.quectel.communication;


import com.quectel.communication.model.ResSerializableBean;

import io.reactivex.rxjava3.functions.BiFunction;


/**
 * Created by xiexiangyu
 * on 2022/3/10
 */

/**
 * 通信定义接口  定义了2个方法
 *  1.  获取数据
 *  2.  定义一个函数式接口BiFunction
 *
 * @param <T>
 */

public interface CommunicationDefinition<T> {
    public ResSerializableBean<T> getData(String data);

    /**
     * BiFunction ：函数式接口
     * 三个参数 :  T1  T2  R
     */

    public BiFunction getBiFunction();
}
