package com.henry.basic.settest;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author: henry.xue
 * @date: 2024-04-10
 */
public class myTreeSet {

    public static void main(String[] args) {
        //使用TreeSet类创建集合
        Set<Integer> numbers = new TreeSet<>();

        // 将元素添加到set集合
        numbers.add(2);
        numbers.add(3);
        numbers.add(1);
        System.out.println("TreeSet: " + numbers);

        //使用iterator()访问元素
        System.out.print("使用iterator()访问元素: ");
        Iterator<Integer> iterate = numbers.iterator();
        while(iterate.hasNext()) {
            System.out.print(iterate.next());
            System.out.print(", ");
        }
    }

}
