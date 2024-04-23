package com.henry.basic.searchalgorithm;

import static com.henry.basic.sortalgorithm.BubbleSort.bubbleSort;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author: henry.xue
 * @date: 2024-04-22
 */
public class mySearch {


    public static int SequenceSearch(int arr[], int value, int n) {
        for (int i = 0; i < n; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static void test1() {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99};
        int result = SequenceSearch(arr, 99, arr.length);
        if (result != -1) {
            System.out.println("目标元素 " + 99 + " 在数组中的索引位置为: " + result);
        } else {
            System.out.println("目标元素 " + 99 + " 未找到");
        }
    }


    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (arr[mid] == target) {
                return mid; // 返回目标元素的索引位置
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1; // 如果未找到目标元素，返回-1
    }

    public static void test2() {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
        int result = binarySearch(arr, 15);
        if (result != -1) {
            System.out.println("目标元素 " + 15 + " 在数组中的索引位置为: " + result);
        } else {
            System.out.println("目标元素 " + 15 + " 未找到");
        }
    }


    public static int interpolationSearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int pos = low + ((target - arr[low]) * (high - low)) / (arr[high] - arr[low]);
            if (arr[pos] == target) {
                return pos; // 返回目标元素的索引位置
            } else if (arr[pos] < target) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }

        return -1; // 如果未找到目标元素，返回-1
    }


    public static void test3() {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
        int result = interpolationSearch(arr, 15);
        if (result != -1) {
            System.out.println("目标元素 " + 15 + " 在数组中的索引位置为: " + result);
        } else {
            System.out.println("目标元素 " + 15 + " 未找到");
        }
    }


    public final static int MAXSIZE = 20;

    /**
     * 斐波那契数列
     *
     * @return
     */
    public static int[] fibonacci() {
        int[] f = new int[20];
        int i = 0;
        f[0] = 1;
        f[1] = 1;
        for (i = 2; i < MAXSIZE; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        System.out.println(Arrays.toString(f));
        return f;
    }

    public static int fibonacciSearch(int[] data, int key) {
        int low = 0;
        int high = data.length - 1;
        int mid = 0;

        //斐波那契分割数值下标
        int k = 0;

        //序列元素个数
        int i = 0;

        // 获取斐波那契数列
        int[] f = fibonacci();

        //获取斐波那契分割数值下标
        while (data.length > f[k] - 1) {
            k++;
        }
        System.out.println("斐波那契数列分割数字： " + k);
        //创建临时数组
        int[] temp = new int[f[k] - 1];
        for (int j = 0; j < data.length; j++) {
            temp[j] = data[j];
        }

        //序列补充至f[k]个元素
        //补充的元素值为最后一个元素的值
        for (i = data.length; i < f[k] - 1; i++) {
            temp[i] = temp[high];
        }

        for (int j : temp) {
            System.out.print(j + " ");
        }
        System.out.println();

        while (low <= high) {
            // low：起始位置
            // 前半部分有f[k-1]个元素，由于下标从0开始
            // 则-1 获取 黄金分割位置元素的下标
            mid = low + f[k - 1] - 1;
            if (temp[mid] > key) {
                // 查找前半部分，高位指针移动
                high = mid - 1;
                //将 k 减去 1，表示我们要查找前半部分。
                // 因为前半部分有 f[k-1] 个元素，所以我们更新 k = k - 1;。
                k = k - 1;
            } else if (temp[mid] < key) {
                // 查找后半部分，低位指针移动
                low = mid + 1;
                //将 k 减去 2，表示我们要查找后半部分。
                // 因为后半部分有 f[k-2] 个元素，所以我们更新 k = k - 2;。
                k = k - 2;
            } else {
                //如果为真则找到相应的位置
                if (mid <= high) {
                    return mid;
                } else {
                    //出现这种情况是查找到补充的元素
                    //而补充的元素与high位置的元素一样
                    return high;
                }
            }
        }
        return -1;
    }


    public static void test4() {
        int[] arr = {12, 11, 15, 50, 7, 65, 3, 99, 100, 101};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println("元素的长度: " + arr.length);
        int result = fibonacciSearch(arr, 101);
        if (result != -1) {
            System.out.println("目标元素 " + 101 + " 在数组中的索引位置为: " + result);
        } else {
            System.out.println("目标元素 " + 101 + " 未找到");
        }
    }


    public static int hashSearch(int[] arr, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        // 将数组元素存入哈希表
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
        }

        // 查找目标元素
        if (map.containsKey(target)) {
            return map.get(target);
        } else {
            return -1;
        }
    }

    public static void test5() {
        int[] arr = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29};
        int target = 17;
        int index = hashSearch(arr, target);
        if (index != -1) {
            System.out.println("目标元素 " + target + " 在数组中的索引为 " + index);
        } else {
            System.out.println("未找到目标元素 " + target);
        }
    }

    public static void main(String[] args) {
        test5();
    }
    
}
