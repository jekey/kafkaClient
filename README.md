##分布式消息系统KAFKA
####高性能KAFKA 的java 客户端 kafkaClient
=======================
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

