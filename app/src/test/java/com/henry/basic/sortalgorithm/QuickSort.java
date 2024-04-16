package com.henry.basic.sortalgorithm;

import java.util.Arrays;

/**
 * @author: henry.xue
 * @date: 2024-04-14
 */
public class QuickSort {
    public static void quickSort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int left, int right) {
        if (left < right) {
            int pivotIdx = partition(arr, left, right);
            sort(arr, 0, pivotIdx - 1);
            sort(arr, pivotIdx + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int idx = left + 1;
        for (int i = idx; i <= right; i++) {
            if (arr[left] > arr[i]) {
                swap(arr, i, idx++);
            }
        }
        swap(arr, left, idx - 1);
        return idx - 1;
    }

    private static void swap(int[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }


    public static void quickSort2(int[] arr) {
        sort2(arr, 0, arr.length - 1);
    }

    private static void sort2(int[] arr, int left, int right) {
        if (left < right) {
            int pivotIdx = partition2(arr, left, right);
            sort2(arr, 0, pivotIdx - 1);
            sort2(arr, pivotIdx + 1, right);
        }
    }

    private static int partition2(int[] arr, int left, int right) {
        int idx = left + 1;
        for (int i = idx; i <= right; i++) {
            if (arr[left] < arr[i]) {
                swap(arr, i, idx++);
            }
        }
        swap(arr, left, idx - 1);
        return idx - 1;
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99, 0};
        System.out.println("---排序前:  " + Arrays.toString(arr));
        quickSort(arr);
        System.out.println("快速排序从小到大:  " + Arrays.toString(arr));
        quickSort2(arr);
        System.out.println("快速排序从大到小:  " + Arrays.toString(arr));
    }

}
