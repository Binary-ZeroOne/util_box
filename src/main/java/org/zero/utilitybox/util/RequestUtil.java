package org.zero.utilitybox.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zero.utilitybox.common.ServerResponse;

/**
 * @ProjectName zhaxinle
 * @Author: zeroJun
 * @Date: 2018/8/17 15:00
 * @Description:
 */
@Slf4j
public class RequestUtil {

    /**
     * 发送数据格式为json的post请求
     *
     * @param url 请求地址
     * @param t   请求数据
     * @param <T> 泛型
     * @return T
     */
    public static <T> ServerResponse postJson(String url, T t) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<T> httpEntity = new HttpEntity<>(t, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ServerResponse> responseEntity = restTemplate.postForEntity(url, httpEntity, ServerResponse.class);
        ServerResponse response = responseEntity.getBody();

        if (response != null && response.isSuccess()) {
            log.info("{}", JsonUtil.obj2String(response));
        } else {
            log.error("{}", JsonUtil.obj2String(response));
        }

        return response;
    }
}
