package com.henry.basic.maptest.treemaptest;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author: henry.xue
 * @date: 2024-04-10
 */
public class mytreemap {

    public static void main(String[] args) {
        TreeMap<String, Integer> scores = new TreeMap<>();
        scores.put("Alice", 90);
        scores.put("Bob", 80);
        scores.put("Charlie", 95);

        System.out.println("Scores: " + scores);

        scores.remove("Bob");
        System.out.println("Scores after removal: " + scores);

        int aliceScore = scores.get("Alice");
        System.out.println("Alice's score: " + aliceScore);

        String firstKey = scores.firstKey();
        String lastKey = scores.lastKey();
        System.out.println("First key: " + firstKey);
        System.out.println("Last key: " + lastKey);
    }


}
