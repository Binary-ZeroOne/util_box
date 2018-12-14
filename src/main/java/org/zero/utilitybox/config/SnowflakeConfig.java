package org.zero.utilitybox.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName superposition
 * @Author: zeroJun
 * @Date: 2018/8/20 16:26
 * @Description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "snowflake.idworker")
public class SnowflakeConfig {
    /**
     * 机器id
     */
    private long workerId;

    /**
     * 数据中心id
     */
    private long datacenterId;

    /**
     * 开始时间截 (默认2015-01-01)
     */
    private long twepoch;

    /**
     * 机器id所占的位数
     */
    private long workerIdBits;

    /**
     * 数据标识id所占的位数
     */
    private long datacenterIdBits;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp;
}
