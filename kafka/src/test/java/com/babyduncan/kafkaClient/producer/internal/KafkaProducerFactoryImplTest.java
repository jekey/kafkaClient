package com.babyduncan.kafkaClient.producer.internal;

import com.babyduncan.kafkaClient.producer.KafkaProducer;
import com.babyduncan.kafkaClient.producer.KafkaProducerFactory;
import com.babyduncan.kafkaClient.thrift.Domainsync;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: guohaozhao babyduncan@qq.com
 * @since: 13-4-17 17:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class KafkaProducerFactoryImplTest {

    private static final Logger logger = Logger.getLogger(KafkaProducerImplTest.class);

    @Test
    public void testGetKafkaProducer() throws Exception {

        KafkaProducer<String, String> kafkaProducer = KafkaProducerFactory.getKafkaProducer();

        for (int i = 0; i < 10; i++) {

            String s = "{\"domain\":\"sohubabyduncan" + i + "\",\"type\":\"0\",\"passport\":\"wb_test_234@sohu.com\",\"sid\":\"2000004436\"}";
            System.out.println(s);
            kafkaProducer.send("domainsync", "zghbbdc" + System.currentTimeMillis());
        }

    }

    @Test
    public void testThriftKafkaProducer() {
        KafkaProducer<String, Domainsync> kafkaProducer = KafkaProducerFactory.getThriftKafkaProducer();

        for (int i = 0; i < 10; i++) {
            Domainsync domainsync = new Domainsync();
            domainsync.setDomain("sohusohu");
            domainsync.setPassport("babyduncan" + i + "@qq.com");
            domainsync.setSid(100000);
            domainsync.setType(0);
            kafkaProducer.send("testthrift", domainsync);
        }
    }
}
