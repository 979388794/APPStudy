package com.henry.basic.settest;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: henry.xue
 * @date: 2024-04-10
 */
public class hashsetTest {


    public static void main(String[] args) {
        //使用HashSet类创建集合
        Set<Integer> set1 = new HashSet<>();

        //将元素添加到set1
        set1.add(2);
        set1.add(3);
        System.out.println("Set1: " + set1);

        //使用HashSet类创建另一个集合
        Set<Integer> set2 = new HashSet<>();

        //添加元素
        set2.add(1);
        set2.add(2);
        System.out.println("Set2: " + set2);

        //两个集合的并集
        set2.addAll(set1);
        System.out.println("并集是: " + set2);
    }
}
