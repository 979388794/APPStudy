package com.henry.basic.sortalgorithm;

import java.util.Arrays;

/**
 * @author: henry.xue
 * @date: 2024-04-18
 */
public class CountingSort {
    public static void countingSort(int[] arr) {
        int len = arr.length;
        if (len < 2) return;
        int minVal = arr[0], maxVal = arr[0];
        for (int i = 1; i < len; i++) {
            if (arr[i] < minVal) {
                minVal = arr[i];
            } else if (arr[i] > maxVal) {
                maxVal = arr[i];
            }
        }

        int[] countArr = new int[maxVal - minVal + 1];
        for (int val : arr) {
            countArr[val - minVal]++;
        }
        for (int arrIdx = 0, countIdx = 0; countIdx < countArr.length; countIdx++) {
            while (countArr[countIdx]-- > 0) {
                arr[arrIdx++] = minVal + countIdx;
            }
        }
    }

    public static void countingSort2(int[] arr) {
        int len = arr.length;
        if (len < 2) return;
        int minVal = arr[0], maxVal = arr[0];
        for (int i = 1; i < len; i++) {
            if (arr[i] < minVal) {
                minVal = arr[i];
            } else if (arr[i] > maxVal) {
                maxVal = arr[i];
            }
        }

        int[] countArr = new int[maxVal - minVal + 1];
        for (int val : arr) {
            countArr[val - minVal]++;
        }
        for (int countIdx = countArr.length - 1, arrIdx = 0; countIdx >= 0; countIdx--) {
            while (countArr[countIdx]-- > 0) {
                arr[arrIdx++] = minVal + countIdx;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99, 0};
        System.out.println("---排序前:  " + Arrays.toString(arr));
        countingSort(arr);
        System.out.println("计数排序从小到大:  " + Arrays.toString(arr));
        countingSort2(arr);
        System.out.println("计数排序从大到小:  " + Arrays.toString(arr));
    }


}
