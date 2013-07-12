package com.babyduncan.mykafka.producer;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * kafka producer µÄ factory
 *
 * @author: guohaozhao babyduncan@qq.com
 * @since: 13-4-17 17:02
 */
public final class KafkaProducerFactory {

    private static final Logger logger = Logger.getLogger(KafkaProducerFactory.class);


    private KafkaProducerFactory() {
    }

    private static class KafkaProducerHolder {
        private static KafkaProducer kafkaProducer = (KafkaProducer) new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        ).getBean("kafkaProducer");

        private static KafkaProducer thriftKafkaProducer = (KafkaProducer) new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        ).getBean("thriftKafkaProducer");
    }

    public static KafkaProducer getKafkaProducer() {
        return KafkaProducerHolder.kafkaProducer;
    }

    public static KafkaProducer getThriftKafkaProducer() {
        return KafkaProducerHolder.thriftKafkaProducer;
    }

}
