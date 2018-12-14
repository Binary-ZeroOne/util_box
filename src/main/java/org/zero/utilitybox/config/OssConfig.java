package org.zero.utilitybox.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ProjectName applicationBox
 * @Author: zeroJun
 * @Date: 2018/8/1 10:43
 * @Description: 阿里云oss相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "alioss.config")
public class OssConfig {
    private String endpoint;
    private String accessKeyId;
    private String host;
    private String accessKeySecret;
    private String bucketName;
    private String callbackUrl;
}
