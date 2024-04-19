package com.henry.basic.sortalgorithm;

import java.util.Arrays;

/**
 * @author: henry.xue
 * @date: 2024-04-18
 */
public class HeapSort {


    public static void main(String[] args) {

        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99,0};
        System.out.println("---排序前:  " + Arrays.toString(arr));
        heapSort(arr);
        System.out.println("堆排序从小到大:  " + Arrays.toString(arr));
        heapSort2(arr);
        System.out.println("堆排序从大到小:  " + Arrays.toString(arr));
    }

    private static int heapLen;
    public static void heapSort(int[] arr) {
        heapLen = arr.length;
        for (int i = heapLen - 1; i >= 0; i--) {
            heapify(arr, i);
        }

        for (int i = heapLen - 1; i > 0; i--) {
            swap(arr, 0, heapLen - 1);
            heapLen--;
            heapify(arr, 0);
        }
    }

    private static void heapify(int[] arr, int idx) {
        int left = idx * 2 + 1, right = idx * 2 + 2, largest = idx;
        if (left < heapLen && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < heapLen && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != idx) {
            swap(arr, largest, idx);
            heapify(arr, largest);
        }
    }


    private static void swap(int[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }
    private static void heapSort2(int[] arr) {
        heapLen = arr.length;
        for (int i = heapLen - 1; i >= 0; i--) {
            heapify2(arr, i);
        }

        for (int i = heapLen - 1; i > 0; i--) {
            swap(arr, 0, heapLen - 1);
            heapLen--;
            heapify2(arr, 0);
        }
    }
    private static void heapify2(int[] arr, int idx) {
        int left = idx * 2 + 1, right = idx * 2 + 2, largest = idx;
        if (left < heapLen && arr[left] < arr[largest]) {
            largest = left;
        }
        if (right < heapLen && arr[right] < arr[largest]) {
            largest = right;
        }

        if (largest != idx) {
            swap(arr, largest, idx);
            heapify2(arr, largest);
        }
    }


}
