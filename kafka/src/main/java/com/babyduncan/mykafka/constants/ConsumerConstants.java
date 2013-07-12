package com.babyduncan.mykafka.constants;

/**
 * @author: guohaozhao babyduncan@qq.com
 * @since: 13-4-15 16:35
 */
public final class ConsumerConstants {
    private ConsumerConstants() {/**/}

    /**
     * 默认的参数
     */
    public static final String[][] DEFAULT_CONFIG = new String[][]{
            {"zk.sessiontimeout.ms", "400"},
            {"zk.connectiontimeout.ms", "6000"},
            {"zk.synctime.ms", "200"},
            {"socket.timeout.ms", "30000"},
            {"socket.buffersize", String.valueOf(64 * 1024)},
            {"fetch.size", String.valueOf(300 * 1024)},
            {"queuedchunks.max", "100"},
            {"autocommit.enable", "true"},
            {"autocommit.interval.ms", "10000"},
            {"autooffset.reset", "smallest"},
            {"rebalance.retries.max", "10"},
            {"groupid", "test_group"}
    };
    /**
     * 固定的参数
     */
    public static final String[][] FIXED_CONFIG = new String[][]{
            {"consumer.timeout.ms", "-1"}
    };
}

