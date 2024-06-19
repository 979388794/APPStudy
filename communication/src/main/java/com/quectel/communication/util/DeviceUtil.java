package com.quectel.communication.util;


/**
 * DeviceUtil 的 Java 类，
 * pingNet，用于执行对特定网址（www.baidu.com）进行网络 ping 测试。
 * Runtime runtime = Runtime.getRuntime();创建一个Runtime对象，用于执行系统命令。
 * return p.waitFor();等待这个进程完成并返回其退出值，即 ping 的结果，一般情况下返回 0 表示成功，其他值表示失败。
 * pingIPNet，用于执行对特定 IP 地址（112.80.248.75）进行网络 ping 测试。
 * ......
 */

public class DeviceUtil {
    private static final String TAG = "DeviceUtil";


    public static int pingNet() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("ping -c 1 www.baidu.com");
            return p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int pingIPNet() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("ping -c 1 112.80.248.75");
            return p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


}
