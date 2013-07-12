package com.babyduncan.kafkaClient.util;

/**
 * @author: guohaozhao babyduncan@qq.com
 * @since: 13-4-17 15:22
 */
public class SystemConfigTest {

    public static void main(String... args) {
        String foo = SystemConfig.getInstance().getString("foo", null);
        System.out.println("foo value is " + foo);
    }
}
