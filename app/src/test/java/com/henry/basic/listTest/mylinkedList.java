package com.henry.basic.listTest;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author: henry.xue
 * @date: 2024-04-11
 */
public class mylinkedList {


    public static void main(String[] args) {


        test1();

    }

    public static void test1() {
        Deque<String> animals = new LinkedList<>();
        //在LinkedList的开始处添加元素
        animals.addFirst("Cow");
        animals.addFirst("Dog");
        animals.addFirst("Cat");
        System.out.println("LinkedList: " + animals);
        //在LinkedList的末尾添加元素
        animals.addLast("Zebra");
        System.out.println("新的LinkedList: " + animals);
    }


}
