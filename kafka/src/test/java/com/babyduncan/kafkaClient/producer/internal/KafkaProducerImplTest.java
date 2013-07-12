package com.babyduncan.kafkaClient.producer.internal;

import com.babyduncan.kafkaClient.producer.KafkaProducer;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author: guohaozhao babyduncan@qq.com
 * @since: 13-4-15 16:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class KafkaProducerImplTest {

    private static final Logger logger = Logger.getLogger(KafkaProducerImplTest.class);

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    /**
     * 测试结果 10万条数据 耗时time use 24452ms 20多秒，每秒4000多条。
     *
     * @throws Exception
     */
    @Test
    public void testSend() throws Exception {
        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            kafkaProducer.send("babyduncanttt", "zghtesthaohaohao" + i);
            logger.info("send one message zghtest" + i);
        }
        long l2 = System.currentTimeMillis();
        logger.info("time use " + (l2 - l1));
    }

    @Test
    public void testSendMultiThread() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    kafkaProducer.send("babyduncanttt", "babyduncan0702");
                    System.out.println("send one !!");
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }

        countDownLatch.await();
        System.out.println("done !!");

    }
}
