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
