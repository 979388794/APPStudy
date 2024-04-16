package com.henry.basic.sortalgorithm;

/**
 * @author: henry.xue
 * @date: 2024-04-14
 */

import java.util.Arrays;

public class MergeSort {

    /**
     * public static int[] copyOfRange(int[] original, int from, int to)
     * original：要复制的原始数组。
     * from：复制的起始索引（包括）。
     * to：复制的结束索引（不包括）。
     *
     * @param arr
     * @return
     */

    public static int[] mergeSort(int[] arr) {
        int len = arr.length;
        if (len < 2) {
            return arr;
        }
        int mIdx = len / 2;
        return merge(mergeSort(Arrays.copyOfRange(arr, 0, mIdx)), mergeSort(Arrays.copyOfRange(arr, mIdx, len)));
    }

    public static int[] mergeSort2(int[] arr) {
        int len = arr.length;
        if (len < 2) {
            return arr;
        }
        int mIdx = len / 2;
        return merge2(mergeSort2(Arrays.copyOfRange(arr, 0, mIdx)), mergeSort2(Arrays.copyOfRange(arr, mIdx, len)));
    }

    private static int[] merge(int[] arrLeft, int[] arrRight) {
        int leftLen = arrLeft.length, rightLen = arrRight.length, leftIdx = 0, rightIdx = 0, idx = 0;
        int[] result = new int[leftLen + rightLen];
        while (leftIdx < leftLen && rightIdx < rightLen) {
            if (arrLeft[leftIdx] < arrRight[rightIdx]) {
                result[idx++] = arrLeft[leftIdx++];
            } else {
                result[idx++] = arrRight[rightIdx++];
            }
        }
        while (leftIdx < leftLen) {
            result[idx++] = arrLeft[leftIdx++];
        }
        while (rightIdx < rightLen) {
            result[idx++] = arrRight[rightIdx++];
        }
        return result;
    }

    private static int[] merge2(int[] arrLeft, int[] arrRight) {
        int leftLen = arrLeft.length, rightLen = arrRight.length, leftIdx = 0, rightIdx = 0, idx = 0;
        int[] result = new int[leftLen + rightLen];
        while (leftIdx < leftLen && rightIdx < rightLen) {
            if (arrLeft[leftIdx] > arrRight[rightIdx]) {
                result[idx++] = arrLeft[leftIdx++];
            } else {
                result[idx++] = arrRight[rightIdx++];
            }
        }
        while (leftIdx < leftLen) {
            result[idx++] = arrLeft[leftIdx++];
        }
        while (rightIdx < rightLen) {
            result[idx++] = arrRight[rightIdx++];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99, 0};
        System.out.println("---排序前:  " + Arrays.toString(arr));
        int[] arr2 = mergeSort(arr);
        System.out.println("归并排序从小到大:  " + Arrays.toString(arr2));
        int[] arr3 = mergeSort2(arr);
        System.out.println("归并排序从大到小:  " + Arrays.toString(arr3));
    }
}
