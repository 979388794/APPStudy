package com.henry.jetPackTest.OkLiveDataBusTest

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.NullPointerException
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 *
 * @author: henry.xue
 * @date: 2024-03-21
 */
object OkLiveDataBusKT {

    //存放订阅者
    private val bus: MutableMap<String, BusMutableLiveData<Any>> by lazy { HashMap() }

    //暴露函数，给外界注册 订阅者关系
    @Synchronized
    fun <T> with(key: String, type: Class<T>, isStick: Boolean = true): BusMutableLiveData<T> {
        if (!bus.containsKey(key)) {
            bus[key] = BusMutableLiveData(isStick)
        }
        return bus[key] as BusMutableLiveData<T>
    }

    class BusMutableLiveData<T> private constructor() : MutableLiveData<T>() {

        var isStick: Boolean = false

        //次构造
        constructor(isStick: Boolean) : this() {
            this.isStick = isStick
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            if (isStick) {
                hook(observer = observer)
                Log.d("Henry", "kotlin 启用黏性");
            } else {
                Log.d("Henry", "kotlin 不启用黏性");
            }
        }

        private fun hook(observer: Observer<in T>) {
            // 获取到LiveData的类中的mObservers对象
            val LivedataClass = LiveData::class.java
            val mObserversField: Field = LivedataClass.getDeclaredField("mObservers")
            mObserversField.isAccessible = true//私有修饰也可以访问

            //获取到这个成员变量的对象
            val mObserversObject: Any = mObserversField.get(this)

            //得到map对象的Class对象
            val mObserversClass: Class<*> = mObserversObject.javaClass

            //获取到mObservers对象的get方法
            val get: Method = mObserversClass.getDeclaredMethod("get", Any::class.java)
            get.isAccessible = true//私有修饰也可以访问

            //执行get方法
            val invokeEntry: Any = get.invoke(mObserversObject, observer)

            //获取Entry的Value  is "AAA" is String       is是判断类型   as是转换类型
            var observerWraper: Any? = null
            if (invokeEntry != null && invokeEntry is Map.Entry<*, *>) {
                observerWraper = invokeEntry.value
            }
            if (observerWraper == null) {
                throw NullPointerException("observerWraper is null")
            }
            //得到observerWraper的类对象
            val supperClass: Class<*> = observerWraper.javaClass.superclass
            val mlastVersion: Field = supperClass.getDeclaredField("mlastVersion");
            mlastVersion.isAccessible = true

            //得到Version
            val mVersion: Field = LivedataClass.getDeclaredField("mVersion")
            mVersion.isAccessible = true

            //mlastVersion=mVersion
            val mVersionValue: Any = mVersion.get(this)
            mlastVersion.set(observerWraper, mVersionValue)


        }
    }
}