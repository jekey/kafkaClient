package com.babyduncan.mykafka.producer.internal;

import com.babyduncan.mykafka.producer.KafkaProducer;
import com.babyduncan.mykafka.util.KafkaUtil;
import kafka.javaapi.producer.Producer;
import kafka.javaapi.producer.ProducerData;
import kafka.producer.ProducerConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: guohaozhao babyduncan@qq.com
 * @since: 13-4-15 16:02
 */
@Service("kafkaProducer")
public class KafkaProducerImpl<K, V> implements KafkaProducer<K, V> {

    private static final Logger LOG = Logger.getLogger(KafkaProducerImpl.class);

    private ProducerConfig producerConfig = KafkaUtil.getDefaultProducerConfig();

    private Producer<K, V> producer = new Producer<K, V>(producerConfig);

    /**
     * 向kafka中send数据
     *
     * @param topic
     * @param key   The default partitioning strategy is hash(key)%numPartitions. If the key is null, then a random broker partition is picked。
     * @param datas
     */
    @Override
    public void send(String topic, K key, List<V> datas) {
        try {
            producer.send(new ProducerData<K, V>(topic, key, datas));
        } catch (Exception e) {
            LOG.error(String.format("kafka send error.topic:[%s],key:[%s],data:[%s]", topic, key, datas), e);
        }
    }

    /**
     * 向kafka中send数据
     *
     * @param topic
     * @param datas
     */
    @Override
    public void send(String topic, List<V> datas) {
        try {
            producer.send(new ProducerData<K, V>(topic, datas));
        } catch (Exception e) {
            LOG.error(String.format("kafka send error.topic:[%s],data:[%s]", topic, datas), e);
        }
    }

    /**
     * 向kafka中send数据
     *
     * @param topic
     * @param data
     */
    @Override
    public void send(String topic, V data) {
        try {
            producer.send(new ProducerData<K, V>(topic, data));
        } catch (Exception e) {
            LOG.error(String.format("kafka send error.topic:[%s],data:[%s]", topic, data), e);
        }
    }
}
