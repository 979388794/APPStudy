package com.henry.basic.searchalgorithm;

/**
 * @author: henry.xue
 * @date: 2024-04-23
 */

import java.util.Scanner;

public class BlockSearch {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //原表
        int a[] = {9, 22, 12, 14, 35, 42, 44, 38, 48, 60, 58, 47, 78, 80, 77, 82};
        //分块获得对应的索引表,这里是一个以索引结点为元素的对象数组
        BlockTable[] arr = {
                new BlockTable(22, 0, 3),//最大关键字为22 起始下标为0,3的块
                new BlockTable(44, 4, 7),
                new BlockTable(60, 8, 11),
                new BlockTable(82, 12, 15)
        };
        //打印原表
        System.out.print("原表元素如下：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
        //待查关键字
        while (true) {
            System.out.print("请输入你所要查询的关键字：");
            int key = input.nextInt();
            //调用分块查找算法，并输出查找的结果
            int result = BlockSearch(a, arr, key);
            System.out.print("查询结果为：" + result);
            System.out.println();
        }
    }

    private static int BlockSearch(int a[], BlockTable[] arr, int key) {
        int left = 0, right = arr.length - 1;
        //利用折半查找法查找元素所在的块
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (arr[mid].key >= key) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        //循环结束，元素所在的块为right+1 取对应左区间下标作为循环的开始点
        int i = arr[right + 1].low;
        //在块内进行顺序查找确定记录的最终位置
        while (i <= arr[right + 1].high && a[i] != key) {
            i++;
        }
        //如果下标在块的范围之内，说明查找成功，佛否则失败
        if (i <= arr[right + 1].high) {
            return i;
        } else {
            return -1;
        }
    }
}

//索引表结点
class BlockTable {
    int key;
    int low;
    int high;

    BlockTable(int key, int low, int high) {
        this.key = key;
        this.low = low;
        this.high = high;
    }
}