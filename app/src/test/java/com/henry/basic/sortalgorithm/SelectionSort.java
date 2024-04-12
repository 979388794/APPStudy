package com.henry.basic.sortalgorithm;

import java.util.Arrays;

/**
 * @author: henry.xue
 * @date: 2024-04-12
 */
public class SelectionSort {

    public static void selectionSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            int minVal = i;
            for (int j = i + 1; j < len; j++) {
                if (arr[minVal] > arr[j]) {
                    minVal = j;
                }
            }
            if (minVal != i) {
                int tmp = arr[i];
                arr[i] = arr[minVal];
                arr[minVal] = tmp;
            }
        }
    }

    public static void selectionSort2(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            int maxVal = i;
            for (int j = i + 1; j < len; j++) {
                if (arr[maxVal] < arr[j]) {
                    maxVal = j;
                }
            }
            if (maxVal != i) {
                int tmp = arr[i];
                arr[i] = arr[maxVal];
                arr[maxVal] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99};
        System.out.println("---排序前:  " + Arrays.toString(arr));
        selectionSort(arr);
        System.out.println("选择排序从小到大:  " + Arrays.toString(arr));
        selectionSort2(arr);
        System.out.println("选择排序从大到小:  " + Arrays.toString(arr));
    }

}
