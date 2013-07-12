## SUC-KAFKA

-----------------

#### 现在支持两种方式的消息类型，一种是基于字符串类型的，一种基于thrift序列化类型的。

1.字符串类型的使用方法如下：

    //Profucer
     KafkaProducer<String, String> kafkaProducer = KafkaProducerFactory.getKafkaProducer();

        for (int i = 0; i < 10; i++) {

            String s = "{\"domain\":\"sohubabyduncan" + i + "\",\"type\":\"0\",\"passport\":\"wb_test_234@sohu.com\",\"sid\":\"2000004436\"}";
            System.out.println(s);
            kafkaProducer.send("domainsync", "zghbbdc" + System.currentTimeMillis());
        }

    //Consumer
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
                        System.out.println("get one message " + new StringDecoder().toEvent((Message) msgAndMetadata.message()));
                    }
                }
            });
        }

2.基于Thrift序列化Model的使用方法：

    //Producer
    KafkaProducer<String, Domainsync> kafkaProducer = KafkaProducerFactory.getThriftKafkaProducer();

        for (int i = 0; i < 10; i++) {
            Domainsync domainsync = new Domainsync();
            domainsync.setDomain("sohusohu");
            domainsync.setPassport("babyduncan" + i + "@qq.com");
            domainsync.setSid(100000);
            domainsync.setType(0);
            kafkaProducer.send("testthrift", domainsync);
        }

    //Consumer
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

    
有问题，联系 ：guohaozhao116008@sohu-inc.com
