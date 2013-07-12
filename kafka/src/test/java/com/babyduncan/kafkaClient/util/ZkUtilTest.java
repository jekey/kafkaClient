package com.babyduncan.kafkaClient.util;

/**
 * @author: guohaozhao babyduncan@qq.com
 * @since: 13-4-17 15:44
 */
public class ZkUtilTest {

    public static void main(String... args) {
        String zk = ZkUtil.getRootPath();
        System.out.println(zk);
    }

}
