package com.henry.basic.sortalgorithm;

import java.util.Arrays;

/**
 * @author: henry.xue
 * @date: 2024-04-12
 */
public class BubbleSort {


    /**
     * flag的作用：flag是对冒泡排序算法的优化，每次内循环结束都会将长度为N-i-1数组中最大的元素交换到最后面，
     * 当内循环结束没有发生数据的交换，说明数组已经是有序的了，此时flag=false，退出循环。
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            boolean flag = true;
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    public static void bubbleSortBack(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            boolean flag = true;
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99};
        System.out.println("---排序前:  " + Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println("从小到大排序后:  " + Arrays.toString(arr));
        bubbleSortBack(arr);
        System.out.println("从大到小排序后:  " + Arrays.toString(arr));
    }

}
