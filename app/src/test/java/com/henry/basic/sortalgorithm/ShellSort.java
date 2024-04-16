package com.henry.basic.sortalgorithm;

import java.util.Arrays;

/**
 * @author: henry.xue
 * @date: 2024-04-12
 */
public class ShellSort {

    public static void shellSort(int[] arr) {
        //声明了整型变量len表示数组长度，以及临时变量tmp和j。
        int len = arr.length, tmp, j;
        //外层循环控制增量gap的大小，初始增量为数组长度的一半，每次循环将增量减半，直到增量为1。
        for (int gap = len / 2; gap >= 1; gap = gap / 2) {
            //内层循环从增量gap开始遍历数组，从第gap个元素开始，对每个元素进行插入排序。
            for (int i = gap; i < len; i++) {
                //将当前元素arr[i]保存到临时变量tmp中。
                tmp = arr[i];
                //初始化变量j为当前元素的前一个元素（间隔gap）的下标
                j = i - gap;
                //在内层循环中，如果j仍在数组范围内且前一个元素大于当前元素，就进行插入排序的操作。
                while (j >= 0 && arr[j] > tmp) {
                    //将前一个元素后移一个间隔位置。
                    arr[j + gap] = arr[j];
                    //继续向前比较。
                    j -= gap;
                }
                //将临时保存的当前元素插入到正确的位置。
                arr[j + gap] = tmp;
            }
        }
    }

    public static void shellSort2(int[] arr) {
        int len = arr.length, tmp, j;
        for (int gap = len / 2; gap >= 1; gap = gap / 2) {
            for (int i = gap; i < len; i++) {
                tmp = arr[i];
                j = i - gap;
                while (j >= 0 && arr[j] < tmp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99,0};
        System.out.println("---排序前:  " + Arrays.toString(arr));
        shellSort(arr);
        System.out.println("希尔排序从小到大:  " + Arrays.toString(arr));
        shellSort2(arr);
        System.out.println("希尔排序从大到小:  " + Arrays.toString(arr));

    }

}
