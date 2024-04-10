package com.henry.basic.settest;

import java.util.LinkedHashSet;

/**
 * @author: henry.xue
 * @date: 2024-04-10
 */
public class mylinkedhashset {

    public static void main(String[] args) {
        test2();
    }




    public static void test1() {
        LinkedHashSet<Integer> evenNumbers = new LinkedHashSet<>();
        evenNumbers.add(2);
        evenNumbers.add(4);
        System.out.println("LinkedHashSet1: " + evenNumbers);

        LinkedHashSet<Integer> numbers = new LinkedHashSet<>();
        numbers.add(1);
        numbers.add(3);
        System.out.println("LinkedHashSet2: " + numbers);

        //两个集合的并集
        numbers.addAll(evenNumbers);
        System.out.println("并集: " + numbers);
    }

    public static void test2() {
        LinkedHashSet<Integer> primeNumbers = new LinkedHashSet<>();
        primeNumbers.add(2);
        primeNumbers.add(3);
        System.out.println("LinkedHashSet1: " + primeNumbers);

        LinkedHashSet<Integer> evenNumbers = new LinkedHashSet<>();
        evenNumbers.add(2);
        evenNumbers.add(4);
        System.out.println("LinkedHashSet2: " + evenNumbers);

        //集合的交集
        evenNumbers.retainAll(primeNumbers);
        System.out.println("集合的交集: " + evenNumbers);
    }


}
