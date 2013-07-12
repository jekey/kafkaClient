package com.babyduncan.mykafka.consumer;

import com.babyduncan.mykafka.message.ThriftMessageSerializer;
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
 * User: guohaozhao (guohaozhao@sohu-inc.com)
 * Date: 13-7-11 17:02
 */
public class KafkaThriftConsumer {

    private static final Logger logger = Logger.getLogger(KafkaConsumer.class);
    private static final int KAFKA_SERVER_CPUS = 4;


    public static void main(String... args) {
        ConsumerConfig consumerConfig = KafkaUtil.getDefaultConsumerConfig();
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("testthrift", KAFKA_SERVER_CPUS);
        Map<String, List<KafkaStream<Message>>> topicMessageStreams =
                consumerConnector.createMessageStreams(map);
        List<KafkaStream<Message>> streams = topicMessageStreams.get("testthrift");
        ExecutorService executor = Executors.newFixedThreadPool(KAFKA_SERVER_CPUS);
        for (final KafkaStream<Message> stream : streams) {
            executor.submit(new Runnable() {
                public void run() {
                    for (MessageAndMetadata msgAndMetadata : stream) {
                        System.out.println(ThriftMessageSerializer.decodeDomainsync((Message) msgAndMetadata.message()));
                    }
                }
            });
        }
    }

}

