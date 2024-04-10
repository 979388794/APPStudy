package com.henry.basic.maptest.linkedhashmap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: henry.xue
 * @date: 2024-04-10
 */
public class mylinkedhashmap {


    public static void main(String[] args) {
        LinkedHashMap<String, Integer> studentScores = new LinkedHashMap<>();
        // 向LinkedHashMap中添加学生姓名和分数
        studentScores.put("Alice", 85);
        studentScores.put("Bob", 90);
        studentScores.put("Cathy", 78);
        studentScores.put("David", 95);

        // 遍历LinkedHashMap并打印每个学生的姓名和分数，保持插入顺序
        for (Map.Entry<String, Integer> entry : studentScores.entrySet()) {
            String studentName = entry.getKey();
            int score = entry.getValue();
            System.out.println("学生姓名: " + studentName + ", 分数: " + score);
        }
        System.out.println("---------------------");
        studentScores.remove("Cathy");
        studentScores.put("Cathy_new", 80);
        // 遍历LinkedHashMap并打印每个学生的姓名和分数，保持插入顺序
        for (Map.Entry<String, Integer> entry : studentScores.entrySet()) {
            String studentName = entry.getKey();
            int score = entry.getValue();
            System.out.println("学生姓名: " + studentName + ", 分数: " + score);
        }

    }


    public static void test() {
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Alice", 90);
        scores.put("Bob", 80);
        scores.put("Charlie", 95);

        System.out.println("Scores: " + scores);

        scores.remove("Bob");
        System.out.println("Scores after removal: " + scores);

        int aliceScore = scores.get("Alice");
        System.out.println("Alice's score: " + aliceScore);

        boolean containsCharlie = scores.containsKey("Charlie");
        System.out.println("Contains Charlie: " + containsCharlie);
    }


}
