package com.henry.basic.listTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author: henry.xue
 * @date: 2024-04-11
 */
public class myArrays {


    public static void main(String[] args) {
        test1();
    }




    public static void test1(){

        //1、使用asList()的String数组，正常
        String[] strings = {"aa", "bb", "cc"};
        List<String> stringList = Arrays.asList(strings);
        System.out.print("1、使用asList()的String数组，正常：  ");
        for(String str : stringList){
            System.out.print(str + " ");
        }
        System.out.println();


        //2、使用asList()的对象类型(Integer)数组，正常
        Integer[] integers = new Integer[] {1, 2, 3};
        List<Integer> integerList = Arrays.asList(integers);
        System.out.print("2、使用asList()的对象类型数组，正常：  ");
        for(int i : integerList){
            System.out.print(i + " ");
        }
        System.out.println();


        //3、使用asList()的基本数据类型数组，出错
        int[] ints = new int[]{1, 2, 3};
        List intList = Arrays.asList(ints);
        System.out.print("3、使用asList()的基本数据类型数组，出错（将'ints'视为单个元素）：");
        for(Object o : intList){
            System.out.print(o.toString());
        }
        System.out.println();

        System.out.print("   " + "要正确输出，需按如下方式遍历：");
        int[] ints1 = (int[]) intList.get(0);
        for(int i : ints1){
            System.out.print(i + " ");
        }
        System.out.println();

        //4、更新数组或List时，另一个将自动更新
        System.out.print("4、更新数组或List时，另一个将自动更新：  ");
        integerList.set(0, 5);
        for(Object o : integerList){
            System.out.print(o + " ");
        }
        for(Object o : integers){
            System.out.print (o + " ");
        }
        System.out.println();

        //5、add() remove() 将报错
        System.out.print("5、add() remove() 将报错：  ");
//        integerList.remove(0);
//        integerList.add(3, 4);
//        integerList.clear();
    }












}
