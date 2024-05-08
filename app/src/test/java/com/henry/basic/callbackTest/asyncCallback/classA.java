package com.henry.basic.callbackTest.asyncCallback;


/**
 * @author: henry.xue
 * @date: 2024-05-08
 */
public class classA {

    public void work(Callable callable)
    {

        System.out.println("classA:收到classB的通知， 我要开始工作了");
        try {
            //模拟classA执行任务
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("classA:我自己的工作完成了，通知一下classB,调用一下它的call方法");
        callable.call();//到了打电话给妈妈
    }

}
