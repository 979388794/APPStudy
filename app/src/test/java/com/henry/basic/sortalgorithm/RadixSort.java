package com.henry.basic.sortalgorithm;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: henry.xue
 * @date: 2024-04-19
 */
public class RadixSort {


    public static void radixSort(int[] arr) {
        if (arr.length < 2)
            return;
        int maxVal = arr[0];//求出最大值
        for (int a : arr) {
            if (maxVal < a) {
                maxVal = a;
            }
        }
        int n = 1;
        while (maxVal / 10 != 0) {//求出最大值位数，即n的迭代次数
            maxVal /= 10;
            n++;
        }

        for (int i = 0; i < n; i++) {//迭代n次
            List<List<Integer>> radix = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                radix.add(new ArrayList<>());//radix包含十个list集合
            }
            int index;
            for (int a : arr) {
                index = (a / (int) Math.pow(10, i)) % 10;//这段代码的作用是获取数字 a 的第 i 位数字。
                radix.get(index).add(a);//radix根据索引为每个list集合添加元素
            }
            index = 0;
            for (List<Integer> list : radix) {
                for (int a : list) {
                    arr[index++] = a;//赋值到数组中
                }
            }
        }
    }


    public static void main(String[] args) {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99, 0};
        System.out.println("---排序前:  " + Arrays.toString(arr));
        radixSort(arr);
        System.out.println("基数排序从小到大:  " + Arrays.toString(arr));
    }

}
