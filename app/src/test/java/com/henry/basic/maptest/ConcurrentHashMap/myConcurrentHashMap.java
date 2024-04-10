package com.henry.basic.maptest.ConcurrentHashMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author: henry.xue
 * @date: 2024-04-10
 */
public class myConcurrentHashMap {


    public static void main(String[] args) {

        test3();
    }

    public static void test1() {
        Map<Long, String> mReqPacket = new HashMap<Long, String>();
        for (long i = 0; i < 15; i++) {
            mReqPacket.put(i, i + "");
        }

        for (Map.Entry<Long, String> entry : mReqPacket.entrySet()) {
            long key = entry.getKey();
            String value = entry.getValue();
            if (key < 10) {
                mReqPacket.remove(key);
            }
        }

        for (Map.Entry<Long, String> entry : mReqPacket.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public static void test3() {
        Map<Long, String> mReqPacket = new ConcurrentHashMap<>();
        for (long i = 0; i < 15; i++) {
            mReqPacket.put(i, i + "");
        }

        for (Map.Entry<Long, String> entry : mReqPacket.entrySet()) {
            long key = entry.getKey();
            String value = entry.getValue();
            if (key < 10) {
                mReqPacket.remove(key);
            }
        }

        for (Map.Entry<Long, String> entry : mReqPacket.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }


    public static void test2() {
        Map<Long, String> conMap = new ConcurrentHashMap<Long, String>();
        for (long i = 0; i < 5; i++) {
            conMap.put(i, i + "");
        }

        Thread thread = new Thread(new Runnable() {
            public void run() {
                conMap.put(100l, "100");
                System.out.println("ADD:" + 100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (Iterator<Map.Entry<Long, String>> iterator = conMap.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<Long, String> entry = iterator.next();
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        thread2.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("--------");
        for (Map.Entry<Long, String> entry : conMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }
}


