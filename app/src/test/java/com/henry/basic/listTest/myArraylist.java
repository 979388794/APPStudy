package com.henry.basic.listTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: henry.xue
 * @date: 2024-04-11
 */
public class myArraylist {


    public static void main(String[] args) {
        test1();
    }


    public static void test1() {
        //使用ArrayList类创建列表
        List<Integer> numbers = new ArrayList<>();

        //将元素添加到列表
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        System.out.println("List: " + numbers);

        //从列表中访问元素
        int number = numbers.get(2);
        System.out.println("访问元素: " + number);

        //从列表中删除元素
        int removedNumber = numbers.remove(1);
        System.out.println("删除元素: " + removedNumber);
    }


}
