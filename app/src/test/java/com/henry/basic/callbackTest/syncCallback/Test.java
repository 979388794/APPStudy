package com.henry.basic.callbackTest.syncCallback;

/**
 * @author: henry.xue
 * @date: 2024-05-08
 */
public class Test {
    public static void main(String[] args) {
        classA A = new classA();
        classB B = new classB(A);
        B.work();
    }
}
