package com.henry.basic.sortalgorithm;

/**
 * @author: henry.xue
 * @date: 2024-04-18
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BucketSort {
    public static void bucketSort(int[] nums) {
        int n = nums.length;
        int mn = nums[0], mx = nums[0];
        // 找出数组中的最大最小值
        for (int i = 1; i < n; i++) {
            mn = Math.min(mn, nums[i]);
            mx = Math.max(mx, nums[i]);
        }
        int size = (mx - mn) / n + 1; // 每个桶存储数的范围大小，使得数尽量均匀地分布在各个桶中，保证最少存储一个
        System.out.println("每个桶的储存数量大小 " + size);
        int cnt = (mx - mn) / size + 1; // 桶的个数，保证桶的个数至少为1
        System.out.println("桶的个数 " + cnt);
        List<Integer>[] buckets = new List[cnt]; // 声明cnt个桶
        for (int i = 0; i < cnt; i++) {
            buckets[i] = new ArrayList<>();//每一个桶都是一个集合
        }
        for (int i = 0; i < n; i++) {
            int number1 = mn + i * size;
            int number2 = mn + (i + 1) * size;
            System.out.println("第" + i + "个桶存理应存放数据范围 " + number1 + "----" + number2);
        }
        // 扫描一遍数组，将数放进桶里
        for (int i = 0; i < n; i++) {
            int idx = (nums[i] - mn) / size;
            buckets[idx].add(nums[i]);
            System.out.println("第" + idx + "个桶存放了" + nums[i]);
        }
        // 对各个桶中的数进行排序，这里用库函数快速排序
        for (int i = 0; i < cnt; i++) {
            buckets[i].sort(null); // 默认是按从小打到排序
        }
        // 依次将各个桶中的数据放入返回数组中
        int index = 0;
        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                nums[index++] = buckets[i].get(j);
            }
        }
    }

    public static void bucketSort2(int[] nums) {
        int n = nums.length;
        int mn = nums[0], mx = nums[0];
        // 找出数组中的最大最小值
        for (int i = 1; i < n; i++) {
            mn = Math.min(mn, nums[i]);
            mx = Math.max(mx, nums[i]);
        }
        int size = (mx - mn) / n + 1; // 每个桶存储数的范围大小，使得数尽量均匀地分布在各个桶中，保证最少存储一个
        int cnt = (mx - mn) / size + 1; // 桶的个数，保证桶的个数至少为1
        List<Integer>[] buckets = new List[cnt]; // 声明cnt个桶
        for (int i = 0; i < cnt; i++) {
            buckets[i] = new ArrayList<>();
        }
        // 扫描一遍数组，将数放进桶里
        for (int i = 0; i < n; i++) {
            int idx = (nums[i] - mn) / size;
            buckets[idx].add(nums[i]);
        }
        // 对各个桶中的数进行排序，这里用库函数快速排序
        for (int i = 0; i < cnt; i++) {
            buckets[i].sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return b - a; // 从大到小排序
                }
            });
        }
        // 依次将各个桶中的数据放入返回数组中
        int index = 0;
        for (int i = cnt - 1; i >= 0; i--) {
            for (int j = 0; j < buckets[i].size(); j++) {
                nums[index++] = buckets[i].get(j);
            }
        }
    }


    public static void main(String[] args) {
        int[] nums = {12, 11, 15, 50, 7, 65, 3, 99};
        System.out.println("排序前 ：" + Arrays.toString(nums));
        bucketSort(nums);
        System.out.println("桶排序从小到大排序后 ：" + Arrays.toString(nums));
        bucketSort2(nums);
        System.out.println("桶排序从大到小排序后 ：" + Arrays.toString(nums));
    }


}