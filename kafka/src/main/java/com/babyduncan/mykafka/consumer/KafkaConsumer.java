package com.babyduncan.mykafka.consumer;

import com.babyduncan.mykafka.util.KafkaUtil;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * kafka的消费者  (模板)
 *
 * @author: guohaozhao babyduncan@qq.com
 * @since: 13-4-15 15:54
 */
public class KafkaConsumer {

    private static final Logger logger = Logger.getLogger(KafkaConsumer.class);
    private static final int KAFKA_SERVER_CPUS = 4;

    /**
     * 测试结果 10万条数据耗时10秒，每秒1万条左右。
     *
     * @param args
     */
    public static void main(String... args) {
        ConsumerConfig consumerConfig = KafkaUtil.getDefaultConsumerConfig();
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("babyduncantest", KAFKA_SERVER_CPUS);
        Map<String, List<KafkaStream<Message>>> topicMessageStreams =
                consumerConnector.createMessageStreams(map);
        List<KafkaStream<Message>> streams = topicMessageStreams.get("babyduncantest");
        ExecutorService executor = Executors.newFixedThreadPool(KAFKA_SERVER_CPUS);
        for (final KafkaStream<Message> stream : streams) {
            executor.submit(new Runnable() {
                public void run() {
                    for (MessageAndMetadata msgAndMetadata : stream) {
//                        logger.info(msgAndMetadata.message().toString());
                        System.out.println("get one message " + new StringDecoder().toEvent((Message) msgAndMetadata.message()));
                    }
                }
            });
        }
    }

}
