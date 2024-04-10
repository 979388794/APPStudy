package com.henry.basic.maptest.hashmaptest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: henry.xue
 * @date: 2024-04-10
 */
public class hashmaptest {


    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        // 创建一个HashMap实例，键为学生姓名（String），值为分数（Integer）
        Map<String, Integer> studentScores = new HashMap<>();

        // 添加学生姓名和分数到HashMap
        studentScores.put("Alice", 85);
        studentScores.put("Bob", 90);
        studentScores.put("Cathy", 78);
        studentScores.put("David", 95);

        // 遍历HashMap并打印每个学生的姓名和分数
        for (Map.Entry<String, Integer> entry : studentScores.entrySet()) {
            String studentName = entry.getKey();
            int score = entry.getValue();
            System.out.println("学生姓名: " + studentName + ", 分数: " + score);
        }
    }
}

