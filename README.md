####高性能KAFKA 的java 客户端 kafkaClient
---------------------------

kafka是一种高吞吐量的分布式发布订阅消息系统，有如下特性：

    1. 通过O(1)的磁盘数据结构提供消息的持久化，这种结构对于即使数以TB的消息存储也能够保持长时间的稳定性能。
    2. 高吞吐量：即使是非常普通的硬件kafka也可以支持每秒数十万的消息。
    3. 支持通过kafka服务器和消费机集群来分区消息。
    4. 支持Hadoop并行数据加载。

卡夫卡的目的是提供一个发布订阅解决方案，它可以处理消费者规模的网站中的所有动作流数据。 这种动作（网页浏览，搜索和其他用户的行动）是在现代网络上的许多社会功能的一个关键因素。 这些数据通常是由于吞吐量的要求而通过处理日志和日志聚合来解决。 对于像Hadoop的一样的日志数据和离线分析系统，但又要求实时处理的限制，这是一个可行的解决方案。kafka的目的是通过Hadoop的并行加载机 制来统一线上和离线的消息处理，也是为了通过集群机来提供实时的消费。


#### kafka的安装
注意：卡夫卡的安装可以离线安装，也可以在线安装，通常对于一个新的环境来说，第一次安装需要使用在线安装的形式，在集群中的一台机器上安装kafka，然后如果需要在其他机器上安装kafka，使用scp或者其他命令copy一份即可使用了。
下面介绍在线安装的过程：

首先下载kafka，下载的地址是：[官网下载](http://kafka.apache.org/downloads.html) ，当前0.7.2 是最新的稳定版本，所以下载这个。
页面上提供了下载地址以及下载密钥，其实最简单的就是直接下载压缩包。
点击下载，下载kafka-0.7.2-incubating-src.tgz
上传到安装的机器上，我的测试机是一台安装在thinkpad t430上的centOs虚拟机，配置如下：

    [root@localhost apps_install]# uname -a
    Linux localhost 2.6.32-358.el6.i686 #1 SMP Thu Feb 21 21:50:49 UTC 2013 i686 i686 i386 GNU/Linux
    [root@localhost apps_install]# cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c
    1  Intel(R) Core(TM) i7-3520M CPU @ 2.90GHz
    [root@localhost apps_install]# getconf LONG_BIT
    32
    
首先，上传刚才下载的压缩包到测试机。

    [root@localhost apps_install]# pwd
    /opt/apps_install
    [root@localhost apps_install]# rz -be
    rz waiting to receive.
    zmodem trl+C ȡ
    100%    1557 KB 1557 KB/s 00:00:01       0 Errors-src.tgz...
    [root@localhost apps_install]# ls
    kafka-0.7.2-incubating-src.tgz
    [root@localhost apps_install]# pwd
    /opt/apps_install
    
然后，解压缩这个包。

    [root@localhost apps_install]# ll
    total 1564
    drwxr-xr-x. 11  501 games    4096 Sep 28  2012 kafka-0.7.2-incubating-src
    -rw-r--r--.  1 root root  1595334 Apr 14 20:56 kafka-0.7.2-incubating-src.tgz
    
下面开始准备安装，由于kafka是scala语言编写的，scala是基于jvm的语言，所以首先需要确定linux系统中安装了jdk，如果没有安装jdk则需要安装jdk。
另外由于是在线安装，所以网络需要畅通。

首先是

    [root@localhost kafka-0.7.2-incubating-src]# ./sbt update
然后是

    [root@localhost kafka-0.7.2-incubating-src]# ./sbt package
    
#### kafka 初探

首先看一下kafka目录下都有些啥。

首先kafka的brokers集群是由zookeeper管理的，那么第一步就是启动zookeeper，如果已经有了一个别的zookeeper集群或者项目组有统一的配置管理中心，那么此步骤可以略过。

    [root@localhost bin]# ./zookeeper-server-start.sh ../config/zookeeper.properties &
    
这样启动了zookeeper。
看一下 zookeeper是否正常启动。

    [root@localhost bin]# ps aux | grep zookeeper
    root     10165  0.0  0.0   5064  1120 pts/1    S    23:25   0:00 /bin/bash ./zookeeper-server-start.sh ../config                /zookeeper.properties
    root     10167  0.0  0.0   5064  1212 pts/1    S    23:25   0:00 /bin/bash ./kafka-run-class.sh org.apache.zookeeper.server.quorum.QuorumPeerMain ../config/zookeeper.properties
    root     10169  0.3  0.6 671624 24780 pts/1    Sl   23:25   0:00 /opt/apps_install/jdk1.6.0_38/bin/java -Xmx512M -server -Dlog4j.configuration=file:./../config/log4j.properties -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -cp :./../project/boot/scala-2.8.0/lib/scala-compiler.jar:./../project/boot/scala-2.8.0/lib/scala-library.jar:./../core/target/scala_2.8.0/kafka-0.7.2.jar:./../core/lib/*.jar:./../perf/target/scala_2.8.0/kafka-perf-0.7.2.jar:./../core/lib_managed/scala_2.8.0/compile/jopt-simple-3.2.jar:./../core/lib_managed/scala_2.8.0/compile/log4j-1.2.15.jar:./../core/lib_managed/scala_2.8.0/compile/snappy-java-1.0.4.1.jar:./../core/lib_managed/scala_2.8.0/compile/zkclient-0.1.jar:./../core/lib_managed/scala_2.8.0/compile/zookeeper-3.3.4.jar org.apache.zookeeper.server.quorum.QuorumPeerMain ../config/zookeeper.properties
    root     10201  0.0  0.0   4356   732 pts/1    S+   23:27   0:00 grep zookeeper
    
正常着，再看看zookeeper的端口。

    [root@localhost bin]# netstat -ntpl| grep 2181
    tcp        0      0 :::2181                     :::*                        LISTEN      10169/java 
    
端口也正常。
接下来启动kafka的broker

    [root@localhost bin]# ./kafka-server-start.sh ../config/server.properties &
    
看看启动是否正常

    [root@localhost bin]# ps aux | grep kafka
    root     10167  0.0  0.0   5064  1212 pts/1    S    23:25   0:00 /bin/bash ./kafka-run-class.sh org.apache.zookeeper.server.quorum.QuorumPeerMain ../config/zookeeper.properties
    root     10169  0.1  0.6 671624 26200 pts/1    Sl   23:25   0:01 /opt/apps_install/jdk1.6.0_38/bin/java -Xmx512M -server -Dlog4j.configuration=file:./../config/log4j.properties -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -cp :./../project/boot/scala-2.8.0/lib/scala-compiler.jar:./../project/boot/scala-2.8.0/lib/scala-library.jar:./../core/target/scala_2.8.0/kafka-0.7.2.jar:./../core/lib/*.jar:./../perf/target/scala_2.8.0/kafka-perf-0.7.2.jar:./../core/lib_managed/scala_2.8.0/compile/jopt-simple-3.2.jar:./../core/lib_managed/scala_2.8.0/compile/log4j-1.2.15.jar:./../core/lib_managed/scala_2.8.0/compile/snappy-java-1.0.4.1.jar:./../core/lib_managed/scala_2.8.0/compile/zkclient-0.1.jar:./../core/lib_managed/scala_2.8.0/compile/zookeeper-3.3.4.jar org.apache.zookeeper.server.quorum.QuorumPeerMain ../config/zookeeper.properties
    root     10240  0.0  0.0   5064  1148 pts/1    S    23:30   0:00 /bin/bash ./kafka-server-start.sh ../config/server.properties
    root     10242  0.0  0.0   5064  1220 pts/1    S    23:30   0:00 /bin/bash ./kafka-run-class.sh kafka.Kafka ../config/server.properties
    root     10244  0.7  1.0 678180 41096 pts/1    Sl   23:30   0:02 /opt/apps_install/jdk1.6.0_38/bin/java -Xmx512M -server -Dlog4j.configuration=file:./../config/log4j.properties -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=9999 -cp :./../project/boot/scala-2.8.0/lib/scala-compiler.jar:./../project/boot/scala-2.8.0/lib/scala-library.jar:./../core/target/scala_2.8.0/kafka-0.7.2.jar:./../core/lib/*.jar:./../perf/target/scala_2.8.0/kafka-perf-0.7.2.jar:./../core/lib_managed/scala_2.8.0/compile/jopt-simple-3.2.jar:./../core/lib_managed/scala_2.8.0/compile/log4j-1.2.15.jar:./../core/lib_managed/scala_2.8.0/compile/snappy-java-1.0.4.1.jar:./../core/lib_managed/scala_2.8.0/compile/zkclient-0.1.jar:./../core/lib_managed/scala_2.8.0/compile/zookeeper-3.3.4.jar kafka.Kafka ../config/server.properties
    root     10361  0.0  0.0   4356   732 pts/1    S+   23:35   0:00 grep kafka
    
接下来启动一个producer，kafka提供了console版本的kafka producer，可以感受一下。

    [root@localhost bin]# ./kafka-console-producer.sh --zookeeper localhost:2181 --topic guohaozhaotest
    
这样我就启动了一个console的producer客户端，使用本地的zookeeper，然后 主题选择的是 guohaozhaotest

接下来再启动一个consumer来消费这个队列，同样是console版本的kafka consumer

    [root@localhost bin]# ./kafka-console-consumer.sh --zookeeper localhost:2181 --topic guohaozhaotest
    
既然是对应的consumer和producer，那么自然zookeeper节点和topic名称 是配套的。

下面测试一下，在producer的窗口中输入一些内容：

    foobar
    hello
    
那么过一小会，可以看到consumer的窗口中显示到了：

    foobar
    hello
    
至此，一次简单的kafka尝试到此就完成了，接下来看看如何在程序里面调用kafka的producer和consumer，以用来把kafka当做一个消息队列使用。

#### kafka的配置

    # Licensed to the Apache Software Foundation (ASF) under one or more
    # contributor license agreements.  See the NOTICE file distributed with
    # this work for additional information regarding copyright ownership.
    # The ASF licenses this file to You under the Apache License, Version 2.0
    # (the "License"); you may not use this file except in compliance with
    # the License.  You may obtain a copy of the License at
    #
    #    http://www.apache.org/licenses/LICENSE-2.0
    #
    # Unless required by applicable law or agreed to in writing, software
    # distributed under the License is distributed on an "AS IS" BASIS,
    # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    # See the License for the specific language governing permissions and
    # limitations under the License.
    # see kafka.server.KafkaConfig for additional details and defaults
    
    ############################# Server Basics #############################
    
    # The id of the broker. This must be set to a unique integer for each broker.
    brokerid=0
    
    # Hostname the broker will advertise to consumers. If not set, kafka will use the value returned
    # from InetAddress.getLocalHost().  If there are multiple interfaces getLocalHost
    # may not be what you want.
    #hostname=
    
    
    ############################# Socket Server Settings #############################
    
    # The port the socket server listens on
    port=9092
    
    # The number of processor threads the socket server uses for receiving and answering requests.
    # Defaults to the number of cores on the machine
    num.threads=8
    
    # The send buffer (SO_SNDBUF) used by the socket server
    socket.send.buffer=1048576
    
    # The receive buffer (SO_RCVBUF) used by the socket server
    socket.receive.buffer=1048576
    
    # The maximum size of a request that the socket server will accept (protection against OOM)
    max.socket.request.bytes=104857600
    
    
    ############################# Log Basics #############################
    
    # The directory under which to store log files
    log.dir=/tmp/kafka-logs
    
    # The number of logical partitions per topic per server. More partitions allow greater parallelism
    # for consumption, but also mean more files.
    num.partitions=1
    
    # Overrides for for the default given by num.partitions on a per-topic basis
    #topic.partition.count.map=topic1:3, topic2:4
    
    ############################# Log Flush Policy #############################
    
    # The following configurations control the flush of data to disk. This is the most
    # important performance knob in kafka.
    # There are a few important trade-offs here:
    #    1. Durability: Unflushed data is at greater risk of loss in the event of a crash.
    #    2. Latency: Data is not made available to consumers until it is flushed (which adds latency).
    #    3. Throughput: The flush is generally the most expensive operation.
    # The settings below allow one to configure the flush policy to flush data after a period of time or
    # every N messages (or both). This can be done globally and overridden on a per-topic basis.
    
    # The number of messages to accept before forcing a flush of data to disk
    log.flush.interval=10000
    
    # The maximum amount of time a message can sit in a log before we force a flush
    log.default.flush.interval.ms=1000
    
    # Per-topic overrides for log.default.flush.interval.ms
    #topic.flush.intervals.ms=topic1:1000, topic2:3000
    
    # The interval (in ms) at which logs are checked to see if they need to be flushed to disk.
    log.default.flush.scheduler.interval.ms=1000
    
    ############################# Log Retention Policy #############################
    
    # Licensed to the Apache Software Foundation (ASF) under one or more
    # contributor license agreements.  See the NOTICE file distributed with
    # this work for additional information regarding copyright ownership.
    # The ASF licenses this file to You under the Apache License, Version 2.0
    # (the "License"); you may not use this file except in compliance with
    # the License.  You may obtain a copy of the License at
    #
    #    http://www.apache.org/licenses/LICENSE-2.0
    #
    # Unless required by applicable law or agreed to in writing, software
    # distributed under the License is distributed on an "AS IS" BASIS,
    # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    # See the License for the specific language governing permissions and
    # limitations under the License.
    # see kafka.server.KafkaConfig for additional details and defaults
    
    ############################# Server Basics #############################
    
    # The id of the broker. This must be set to a unique integer for each broker.
    brokerid=0
    
    # Hostname the broker will advertise to consumers. If not set, kafka will use the value returned
    # from InetAddress.getLocalHost().  If there are multiple interfaces getLocalHost
    # may not be what you want.
    #hostname=
    
    
    ############################# Socket Server Settings #############################
    
    # The port the socket server listens on
    port=9092
    
    # The number of processor threads the socket server uses for receiving and answering requests.
            
需要修改的配置:
从配置的注解非常明了，那么我们需要改哪些配置呢？
首先是brokerid，由于目前我们只启动了一个broker。所以无需修改，如果是多个实例的话，brokerid需要名字不同。
然后是host，这里我们需要修改为broker的ip地址，执行以下命令：
    
        [root@localhost bin]# ip a
        1: lo: <LOOPBACK,UP,LOWER_UP> mtu 16436 qdisc noqueue state UNKNOWN
            link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
            inet 127.0.0.1/8 scope host lo
            inet6 ::1/128 scope host
               valid_lft forever preferred_lft forever
        2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP qlen 1000
            link/ether 00:0c:29:74:91:2d brd ff:ff:ff:ff:ff:ff
            inet 192.168.118.129/24 brd 192.168.118.255 scope global eth0
            inet6 fe80::20c:29ff:fe74:912d/64 scope link
               valid_lft forever preferred_lft forever
               
那么我需要修改host为：192.168.118.129
修改之后，重启一下kafka的server，先stop，再start。
    
    ../bin/kafka-server-stop
    ../bin/kafka-server-start.sh ./server.properties &
    
看一下是否正常

    [root@localhost config]# ps aux | grep kafka
    root     10167  0.0  0.0   5064  1212 pts/1    S    23:25   0:00 /bin/bash ./kafka-run-class.sh org.apache.zookeeper.server.quorum.QuorumPeerMain ../config/zookeeper.properties
    root     10169  0.1  0.7 671624 29908 pts/1    Sl   23:25   0:02 /opt/apps_install/jdk1.6.0_38/bin/java -Xmx512M -server -Dlog4j.configuration=file:./../config/log4j.properties -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -cp :./../project/boot/scala-2.8.0/lib/scala-compiler.jar:./../project/boot/scala-2.8.0/lib/scala-library.jar:./../core/target/scala_2.8.0/kafka-0.7.2.jar:./../core/lib/*.jar:./../perf/target/scala_2.8.0/kafka-perf-0.7.2.jar:./../core/lib_managed/scala_2.8.0/compile/jopt-simple-3.2.jar:./../core/lib_managed/scala_2.8.0/compile/log4j-1.2.15.jar:./../core/lib_managed/scala_2.8.0/compile/snappy-java-1.0.4.1.jar:./../core/lib_managed/scala_2.8.0/compile/zkclient-0.1.jar:./../core/lib_managed/scala_2.8.0/compile/zookeeper-3.3.4.jar org.apache.zookeeper.server.quorum.QuorumPeerMain ../config/zookeeper.properties
    root     10819  0.0  0.0   5064  1152 pts/1    S    23:54   0:00 /bin/bash ../bin/kafka-server-start.sh ./server.properties
    root     10821  0.0  0.0   5064  1224 pts/1    S    23:54   0:00 /bin/bash ../bin/kafka-run-class.sh kafka.Kafka ./server.properties
    root     10823  1.4  1.0 678240 41596 pts/1    Sl   23:54   0:01 /opt/apps_install/jdk1.6.0_38/bin/java -Xmx512M -server -Dlog4j.configuration=file:../bin/../config/log4j.properties -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=9999 -cp :../bin/../project/boot/scala-2.8.0/lib/scala-compiler.jar:../bin/../project/boot/scala-2.8.0/lib/scala-library.jar:../bin/../core/target/scala_2.8.0/kafka-0.7.2.jar:../bin/../core/lib/*.jar:../bin/../perf/target/scala_2.8.0/kafka-perf-0.7.2.jar:../bin/../core/lib_managed/scala_2.8.0/compile/jopt-simple-3.2.jar:../bin/../core/lib_managed/scala_2.8.0/compile/log4j-1.2.15.jar:../bin/../core/lib_managed/scala_2.8.0/compile/snappy-java-1.0.4.1.jar:../bin/../core/lib_managed/scala_2.8.0/compile/zkclient-0.1.jar:../bin/../core/lib_managed/scala_2.8.0/compile/zookeeper-3.3.4.jar kafka.Kafka ./server.properties
    root     10877  0.0  0.0   4356   736 pts/1    S+   23:55   0:00 grep kafka

启动正常。

接下来开始编码前的最后一项准备，引入jar包。
编写kafka的producer和consumer需要引入一下两个包：

    `./core/target/scala_2.8.0/kafka-0.7.2.jar
    ./core/lib_managed/scala_2.8.0/compile/zkclient-0.1.jar`

这两个包是在kafka 执行 sbt update的时候下载下来的，我把这俩包发布到maven仓库中，这样可以方便打包发布。
那么在编写kafka应用程序的时候，需要引入一下依赖：
    
    <dependency>
      <groupId>kafka</groupId>
      <artifactId>kafka</artifactId>
      <version>0.7.2</version>
    </dependency>
    
#### kafka的一些重要配置

##### 首先是broker的配置

    名称    默认值  描述
    brokerid 	none
    	broker的唯一名字，唯一id，各个broker需要配置不同的int值，一般从0开始配置。
    enable.zookeeper 	true
    	是否允许使用zookeeper，kafka可以使用zookeeper也可以ip+port直连，当然使用zk最好。
    log.flush.interval 	500
    	message flush到db的条数，500的意思是message没积攒到500条，flush到db一次，然后这500条就可以被consumer消费了。
    log.default.flush.scheduler.interval.ms 	3000
    	检查flush的定时任务，3000的意思是每3秒去检查一次，message是否应该被flush到db上。
    log.default.flush.interval.ms 	log.default.flush.scheduler.interval.ms 	如果有多个log.default.flush.scheduler.interval.ms配置，那么这个是其中最大的那个值。
    topic.flush.intervals.ms 	none
    	每一个topic的配置：多长时间把message flush到db。针对某个或者某些topic具体配置，而不使用通用配置。
    log.retention.hours 	168
    	日志在db上保存的时间，168小时就是一星期。
    topic.log.retention.hours 	none
    	针对每一个topic 配置其log的保留时间，覆盖log.retention.hours。
    log.retention.size 	-1
    	当一个log文件超过多大的时候就删除，默认是无限大，也就是硬盘最大容量那么大。
    log.cleanup.interval.mins 	10
    	10的意思是每10分钟检查一下是否有一些日志应该被删除了，可能是超过了168小时或者日志的体积大于了最大体积。
    log.dir 	none 	日志保存的目录。
    log.file.size 	1024*1024*1024
    	单个文件的最大体积 1024*1024*1024 就是1G 。
    max.socket.request.bytes 	104857600 	socket请求的最大字节 104857600就是100M。
    monitoring.period.secs 	600
    	每10分钟统计一下性能
    num.threads 	Runtime.getRuntime().availableProcessors 	broker的线程数，Runtime.getRuntime().availableProcessors的意思就是几核的cpu就开几个线程。
    num.partitions 	1 	一个topic有几个partition，默认一个topic一个partition，也就是说一个topic默认只能有一个consumer，墙裂建议这个要开大一些。
    socket.send.buffer 	102400 	socket send buffer的大小
    socket.receive.buffer 	102400 	socket receive buffer的大小
    topic.partition.count.map 	none
    	为每一个topic分别配置partition的个数，比如：topic1:10,topic2:20。
    zk.connect 	localhost:2182/kafka 	kafka 的zk连接
    zk.connectiontimeout.ms 	6000
    	zk的connection超时时间
    zk.sessiontimeout.ms 	6000
    	zk的session的超时时间
    zk.synctime.ms 	2000
    	zk的同步时间 

##### consumer的重要配置
    
        名称    默认值  描述
    groupid 	groupid 	consumer的组id，一般一组consumer消费一个topic，这些consumer配置共同的groupid。
    socket.timeout.ms 	30000
    	socket超时时间 30秒，为什么这么大？因为kafka写硬盘的，对consumer一点都不着急。
    socket.buffersize 	64*1024
    	64k，socket的buffer 大小。
    fetch.size 	300*1024
    	每次从broker 获取300k的数据。
    backoff.increment.ms 	1000
    	如果从一个broker节点获得了空，那么就等到1秒之后再去pull，而不是取不到还一直取，直到取到。
    queuedchunks.max 	100
    	consumer开100个blocking queue用于缓存从server获得的消息。
    autocommit.enable 	true
    	consumer自动的把消费的offset commit到zk中。
    autocommit.interval.ms 	10000
    	consumer每10秒把消费的offset commit到zk中。
    autooffset.reset 	smallest 	重置server的offset为最小 largest为重置为最大，其他值会导致抛异常。
    consumer.timeout.ms 	-1 	-1的意思就是consumer或得不到数据的话就一直阻塞，如果是一个特定的值，那么超过那么多秒就抛出异常。
    rebalance.retries.max 	4
    	rebalance的最大次数，尚不得知超过了这个次数是不balance了还是抛异常。个人觉得是就不balance了，爱咋咋地了。 
        
##### producer的重要配置

    名称    默认值  说明
    serializer.class 	kafka.serializer.DefaultEncoder 	实现kafka.serializer.Encoder<T>的一个类的类名，把T适配成kafka的Message。
    partitioner.class 	kafka.producer.DefaultPartitioner<T>也就是hash(key)%num_partitions 	分区算法，null的话就是随机取，可以实现kafka.producer.Partitioner<K>接口来自定义。
    producer.type 	sync 	生产者模式，同步或者异步async
    broker.list 	null
    	如果不使用zk来管理，这里配置直连的ip+port
    zk.connect 	null
    	使用zk管理的话，这里配置zk地址。
    buffer.size 	102400
    	socket buffer size 100k
    connect.timeout.ms 	5000
    	连接超时时间 5秒
    socket.timeout.ms 	30000
    	socket超时时间30秒
    reconnect.interval 	30000
    	重连时间 30秒
    max.message.size 	1000000 	message的payload最大值1000000字节。
    compression.codec 	0
    	压缩算法0不压缩1gzip压缩2Snappy，一般选择gzip，但是使用Snappy压缩效率更高。
    compressed.topics 	null
    	对topic分别设置压缩算法
    zk.read.num.retries 	3
    	zk刷新缓存的重试次数
        
##### 下面是异步的producer的重要配置

        名称    默认值  配置
    queue.time 	5000 	异步队列缓冲数据的时间5秒
    queue.size 	10000 	异步队列的大小10k
    batch.size 	200
    	批量大小200条
    event.handler 	kafka.producer.async.EventHandler<T> 	事件处理器
    event.handler.props 	null
    	事件处理器属性
    callback.handler 	null
    	回调处理器
    callback.handler.props 	null
    	回调处理器属性
        

#### 对kafka的运维和监控

硬件方面

linkedin表示kafka对cpu的要求比较低，尤其是使用GZIP压缩算法的时候，对cpu的要求更低。

对内存的要求也不高，内存的使用大多用于对热log的缓存上。

由于kafka是写硬盘的，所以硬盘的读写速度直接关系着kafka的性能，硬盘的读写越快越好，硬盘的容量越大越好。

对于硬盘需要有硬盘容量的监控。
操作系统方面

linkedin 表示最好对操作系统做如下优化：

1，增加操作系统文件描述符的数量。因为kafka是要读写log文件的，那么当然文件描述符越多就可以操作越多的log了，这样kafka的性能会好一些。

先看一下 文件描述符的值：

    [root@localhost opt]# ulimit -n
    1024

意思就是这台机器上的每个进程最多可以同时打开1024个文件。

调整这个数值，把这个数调整到最大：

    [root@localhost opt]# ulimit -HSn 65536
    [root@localhost opt]# ulimit -n
    65536

2，增加max socket buffer size数值，以获得更高的性能。

    cat /proc/sys/net/ipv4/tcp_moderate_rcvbuf
    1

说明max socket buffer size是默认的4MB

验证一下：

    [root@localhost opt]# cat /proc/sys/net/ipv4/tcp_rmem
    4096    87380   2867200
    [root@localhost opt]# cat /proc/sys/net/ipv4/tcp_wmem
    4096    16384   2867200

那么通过设置这些值来增加buffer size，目前尚不得知linkedin设置的多少，推荐的设置为：

    echo 1 > /proc/sys/net/ipv4/tcp_moderate_rcvbuf
           echo 108544 > /proc/sys/net/core/wmem_max
           echo 108544 > /proc/sys/net/core/rmem_max
           echo "4096 87380 4194304" > /proc/sys/net/ipv4/tcp_rmem
           echo "4096 16384 4194304" > /proc/sys/net/ipv4/tcp_wmem

java虚拟机

首先看一下java的版本：

    [root@localhost opt]# java -version
    java version "1.6.0_38-ea"
    Java(TM) SE Runtime Environment (build 1.6.0_38-ea-b04)
    Java HotSpot(TM) Client VM (build 20.13-b02, mixed mode, sharing)

linkedin推荐使用jdk1.6 并且推荐的程序启动参数为：

    java -server -Xms3072m -Xmx3072m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70
         -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -Xloggc:logs/gc.log -Djava.awt.headless=true
         -Dcom.sun.management.jmxremote -classpath <long list of jars>
    
    kafka

kafka推荐使用最新的版本，因为有更少的bug。

kafka server的最重要的配置就是flush到硬盘的频率，如果过高，那么势必影响性能，如果过低，又不够实时性，所以需要采取一个折中的方案。

kafka提供了timeout 和max message 两种flush机制，就是一种是积累到一定时间flush一次，一种是buffer到了一定数量flush一次。

对于客户端最重要的配置为：

    1，压缩算法，默认配置是不压缩，可选的压缩有0不压缩，1Gzip以及2Snappy ，看一般都使用gzip，比较节省cpu，而Snappy则是超级快。会比gzip高出50倍左右，cpu会高出10%多，需要看情况选择。
    
    2，produce方式，同步和异步，一般选择异步。
    
    3，batch size
    
    4，fetch size

下面是linkedin推荐server的配置：

    kafka.log.default.flush.interval.ms=10000
    kafka.log.file.size=1073741824
    kafka.log.default.flush.scheduler.interval.ms=2000
    kafka.log.flush.interval=3000kafka.socket.send.buffer=2097152
    kafka.socket.receive.buffer=2097152
    kafka.monitoring.period.secs=30
    kafka.num.threads=8
    kafka.log.cleanup.interval.mins=30
    kafka.log.retention.hours=168
    kafka.zookeeper.sessiontimeoutms=6000
    kafka.zookeeper.connection.timeout=2000
    kafka.num.partitions=1

对于客户端的配置需要根据各自的场景的不同来配置了。
对kafka状态的监控

linkedin推荐使用JMX对kafka进行监控，下面举例：

比如使用jsonsole的话：以我的测试机为例，remote输入service:jmx:rmi:///jndi/rmi://192.168.118.129:9999/jmxrmi 连上jmx之后就可以看一些kafka的信息了。

kafka

一些重要的变量值为：

    Server Stats
    
        bean name: kafka:type=kafka.SocketServerStats
    
          def getProduceRequestsPerSecond: Double
          def getFetchRequestsPerSecond: Double
          def getAvgProduceRequestMs: Double
          def getMaxProduceRequestMs: Double
          def getAvgFetchRequestMs: Double
          def getMaxFetchRequestMs: Double
          def getBytesReadPerSecond: Double
          def getBytesWrittenPerSecond: Double
          def getNumFetchRequests: Long
          def getNumProduceRequests: Long
          def getTotalBytesRead: Long
          def getTotalBytesWritten: Long
          def getTotalFetchRequestMs: Long
          def getTotalProduceRequestMs: Long
    
        bean name: kafka:type=kafka.BrokerAllTopicStat kafka:type=kafka.BrokerAllTopicStat.[topic]
    
          def getMessagesIn: Long
          def getBytesIn: Long
          def getBytesOut: Long
          def getFailedProduceRequest: Long
          def getFailedFetchRequest: Long
    
        bean name: kafka:type=kafka.LogFlushStats
    
          def getFlushesPerSecond: Double
          def getAvgFlushMs: Double
          def getTotalFlushMs: Long
          def getMaxFlushMs: Double
          def getNumFlushes: Long
    
    Producer stats
    
        bean name: kafka:type=kafka.KafkaProducerStats
    
          def getProduceRequestsPerSecond: Double
          def getAvgProduceRequestMs: Double
          def getMaxProduceRequestMs: Double
          def getNumProduceRequests: Long
    
        bean name: kafka.producer.Producer:type=AsyncProducerStats
    
          def getAsyncProducerEvents: Int
          def getAsyncProducerDroppedEvents: Int
    
    Consumer stats
    
        bean name: kafka:type=kafka.ConsumerStats
    
          def getPartOwnerStats: String
          def getConsumerGroup: String
          def getOffsetLag(topic: String, brokerId: Int, partitionId: Int): Long
          def getConsumedOffset(topic: String, brokerId: Int, partitionId: Int): Long
          def getLatestOffset(topic: String, brokerId: Int, partitionId: Int): Long
    
        bean name: kafka:type=kafka.ConsumerAllTopicStat kafka:type=kafka.ConsumerTopicStat.[topic]
    
          def getMessagesPerTopic: Long
          def getBytesPerTopic: Long
    
        bean name: kafka:type=kafka.SimpleConsumerStats
    
          def getFetchRequestsPerSecond: Double
          def getAvgFetchRequestMs: Double
          def getMaxFetchRequestMs: Double
          def getNumFetchRequests: Long
          def getConsumerThroughput: Double

对zookeeper的运维

kafka是依赖zookeeper做管理的，那么一个健康的kafka需要有一个健康的zookeeper来维护。

对于zookeeper的运维，linkedin给了以下建议：

    1，尽量对物理层，网络层，硬件等设施做冗余 。保持电源和网络可用。
    
    2，io隔离，对于zookeeper来说，事务日志比程序日志或者日志的快照更好，把程序日志同步然后读取的方式是比较慢的。
    
    3，应用程序分开部署。
    
    4，小心虚拟化。zookeeper对虚拟化技术支持不是很好，可能会导致一些问题。
    
    5，对zookeeper的配置和监控。
    
    6，不要建设大型集群，在满足需要的情况下尽可能小和简单。
    
    7，尽量建设一个3-5实例的zookeeper集群，这样既可以满足zk的功能，同时集群又足够精简和小巧。

