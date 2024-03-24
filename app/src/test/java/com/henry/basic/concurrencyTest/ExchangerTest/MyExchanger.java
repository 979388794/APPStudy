package com.henry.basic.concurrencyTest.ExchangerTest;

import java.util.concurrent.Exchanger;

/**
 * @author: henry.xue
 * @date: 2024-03-19
 */


public class MyExchanger {


    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        Exchanger<String> exchanger = new Exchanger<>();
        Thread ming = new Thread(() -> {
            try {
                String mingData = "小明的商品A";
                System.out.println("小明有：" + mingData);
                String hongData = exchanger.exchange(mingData);
                System.out.println("小明收到：" + hongData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread hong = new Thread(() -> {
            try {
                String hongData = "小红的商品B";
                System.out.println("小红有：" + hongData);
                String mingData = exchanger.exchange(hongData);
                System.out.println("小红收到：" + mingData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread gang = new Thread(() -> {
            try {
                String gangData = "小刚的商品C";
                System.out.println("小刚有：" + gangData);
                String elseData = exchanger.exchange(gangData);
                System.out.println("小刚收到：" + elseData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread hua = new Thread(() -> {
            try {
                String huaData = "小花的商品D";
                System.out.println("小花有：" + huaData);
                String elseData = exchanger.exchange(huaData);
                System.out.println("小花收到：" + elseData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        ming.start();
        hong.start();
        gang.start();
        hua.start();

    }


}
