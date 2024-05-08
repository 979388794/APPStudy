package com.henry.basic.callbackTest.syncCallback;

/**
 * @author: henry.xue
 * @date: 2024-05-08
 */
public class classB implements Callable {

    //定义类classB实现了Callable接口，实现了call()回调函数的具体内容
    classA A;

    public classB(classA A) {
        this.A = A;
    }


    public void work() {
        System.out.println("classB:我要开始工作了 顺便通知一下classA 开始工作");
        A.work(this);
        System.out.println("classB：classA已经完成工作了");
    }

    @Override
    public void call() {
        System.out.println("classB：执行了自己的call 方法");
    }

}
