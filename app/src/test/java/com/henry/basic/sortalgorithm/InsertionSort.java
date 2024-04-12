package com.henry.basic.sortalgorithm;

import java.util.Arrays;

/**
 * @author: henry.xue
 * @date: 2024-04-12
 */
public class InsertionSort {

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int val = arr[i], j = i;
            while (j > 0 && val < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = val;
        }
    }

    public static void insertionSort2(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int val = arr[i], j = i;
            while (j > 0 && val > arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = val;
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99};
        System.out.println("---排序前:  " + Arrays.toString(arr));
        insertionSort(arr);
        System.out.println("从小到大插入排序后:  " + Arrays.toString(arr));
        insertionSort2(arr);
        System.out.println("从大到小插入排序后:  " + Arrays.toString(arr));
    }
}
