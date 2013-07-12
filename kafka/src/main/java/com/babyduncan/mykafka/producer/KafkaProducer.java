package com.babyduncan.mykafka.producer;

import java.util.List;

/**
 * @author: guohaozhao babyduncan@qq.com
 * @since: 13-4-15 16:01
 */
public interface KafkaProducer<K, V> {
    /**
     * 向消息系统中send数据 一个主题的随机分区，发送一条个数据。
     *
     * @param topic
     * @param data
     */
    public void send(String topic, V data);

    /**
     * 向消息系统中send数据 一个主题的随机分区，发送多条个数据。
     *
     * @param topic
     * @param datas
     */
    public void send(String topic, List<V> datas);

    /**
     * 向消息系统中send数据 一个主题的指定的一个分区，发送多条个数据。
     *
     * @param topic
     * @param key
     * @param datas
     */
    public void send(String topic, K key, List<V> datas);

}
