package org.zero.utilitybox.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 *
 * @author Forty Seven
 * @date 2019-01-08
 */
@Slf4j
public class HttpRest {
    /**
     * 发送有参数post请求，返回类型支持指定泛型
     *
     * @param url           请求url
     * @param param         参数对象
     * @param typeReference 返回类型
     * @return T
     */
    public static <P, T> T post(String url, P param, ParameterizedTypeReference<T> typeReference) {
        return getRestInstance().exchange(url, HttpMethod.POST, getHttpEntity(param, true), typeReference).getBody();
    }

    /**
     * 发送有参数post请求
     *
     * @param url      请求url
     * @param param    参数对象
     * @param respType 返回类型
     * @return T
     */
    public static <P, T> T post(String url, P param, Class<T> respType) {
        return getRestInstance().postForEntity(url, getHttpEntity(param, true), respType).getBody();
    }

    /**
     * 发送有参数post请求
     *
     * @param url   请求url
     * @param param 参数对象
     */
    public static <P> void post(String url, P param) {
        getRestInstance().postForEntity(url, getHttpEntity(param, true), null);
    }

    /**
     * 发送有参数的异步post请求
     *
     * @param url      请求url
     * @param param    参数对象
     * @param callback 回调接口
     * @param respType 返回类型
     */
    public static <P, T> void asyncPost(String url, P param, Class<T> respType, ListenableFutureCallback<ResponseEntity<T>> callback) {
        getAsyncRestInstance().postForEntity(url, getHttpEntity(param, true), respType).addCallback(callback);
    }

    /**
     * 发送无参数post请求
     *
     * @param url      请求url
     * @param respType 返回类型
     * @return T
     */
    public static <T> T post(String url, Class<T> respType) {
        return getRestInstance().postForObject(url, null, respType);
    }

    /**
     * 发送无参数的异步post请求
     *
     * @param url      请求url
     * @param callback 回调接口
     * @param respType 返回类型
     */
    public static <T> void asyncPost(String url, Class<T> respType, ListenableFutureCallback<ResponseEntity<T>> callback) {
        asyncPost(url, null, respType, callback);
    }

    /**
     * 发送表单参数的post请求
     *
     * @param url      请求url
     * @param param    参数
     * @param respType 返回类型
     * @return T
     */
    public static <T> T postForm(String url, Map<String, List<String>> param, Class<T> respType) {
        return getRestInstance().postForEntity(url, getHttpEntity(param, false), respType).getBody();
    }

    /**
     * 发送表单参数的post请求
     *
     * @param url   请求url
     * @param param 参数
     */
    public static void postForm(String url, Map<String, List<String>> param) {
        getRestInstance().postForEntity(url, getHttpEntity(param, false), null);
    }

    /**
     * 发送表单参数的异步post请求
     *
     * @param url      请求url
     * @param callback 回调接口
     * @param respType 返回类型
     */
    public static <T> void asyncPostForm(String url, Map<String, List<String>> param,
                                         Class<T> respType, ListenableFutureCallback<ResponseEntity<T>> callback) {
        getAsyncRestInstance().postForEntity(url, getHttpEntity(param, false), respType).addCallback(callback);
    }

    /**
     * 发送有参数get请求
     *
     * @param url      请求url
     * @param param    参数对象
     * @param respType 返回类型
     * @return T
     */
    public static <P, T> T get(String url, P param, Class<T> respType) {
        return getRestInstance().getForEntity(url, respType, getHttpEntity(param, true)).getBody();
    }

    /**
     * 发送有参数的异步get请求
     *
     * @param url      请求url
     * @param param    参数对象
     * @param callback 回调接口
     * @param respType 返回类型
     */
    public static <P, T> void asyncGet(String url, P param, Class<T> respType, ListenableFutureCallback<ResponseEntity<T>> callback) {
        getAsyncRestInstance().getForEntity(url, respType, getHttpEntity(param, true)).addCallback(callback);
    }

    /**
     * 发送get请求
     *
     * @param url      请求url
     * @param respType 返回类型
     * @return T
     */
    public static <T> T get(String url, Class<T> respType) {
        return getRestInstance().getForObject(url, respType);
    }

    /**
     * 发送无参数的异步get请求
     *
     * @param url      请求url
     * @param callback 回调接口
     * @param respType 返回类型
     */
    public static <T> void asyncGet(String url, Class<T> respType, ListenableFutureCallback<ResponseEntity<T>> callback) {
        asyncGet(url, null, respType, callback);
    }

    /**
     * 获取HttpEntity实例对象
     *
     * @param param  参数对象
     * @param isJson true 发送json请求,false发送表单请求
     * @return HttpEntity
     */
    private static <P> HttpEntity<P> getHttpEntity(P param, boolean isJson) {
        HttpHeaders headers = new HttpHeaders();
        if (isJson) {
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        } else {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        return new HttpEntity<>(param, headers);
    }

    /*-----------------生产单例对象，方便自定义如何构造对象------------------*/

    private static RestTemplate restInit() {
        return new RestTemplate();
    }

    private static AsyncRestTemplate asyncRestInit() {
        return new AsyncRestTemplate();
    }

    private static RestTemplate getRestInstance() {
        return RestSingle.INSTANCE;
    }

    private static AsyncRestTemplate getAsyncRestInstance() {
        return AsyncRestSingle.INSTANCE;
    }

    private static class RestSingle {
        private static final RestTemplate INSTANCE = restInit();
    }

    private static class AsyncRestSingle {
        private static final AsyncRestTemplate INSTANCE = asyncRestInit();
    }
}
