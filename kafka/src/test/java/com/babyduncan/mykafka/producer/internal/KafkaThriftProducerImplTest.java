package com.babyduncan.mykafka.producer.internal;

import com.babyduncan.mykafka.producer.KafkaProducer;
import com.babyduncan.mykafka.thrift.Domainsync;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: guohaozhao (guohaozhao@sohu-inc.com)
 * Date: 13-7-11 17:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class KafkaThriftProducerImplTest {

    private static final Logger logger = Logger.getLogger(KafkaThriftProducerImplTest.class);

    @Autowired
    @Qualifier("thriftKafkaProducer")
    private KafkaProducer<String, Domainsync> kafkaProducer;

    @Test
    public void testSend() throws Exception {

        for (int i = 0; i < 10; i++) {
            Domainsync domainsync = new Domainsync();
            domainsync.setDomain("sohu");
            domainsync.setPassport("babyduncan" + i + "@qq.com");
            domainsync.setSid(100000);
            domainsync.setType(0);
            kafkaProducer.send("testthrift", domainsync);
        }

        System.out.println("done !!");


    }
}
