package org.zero.utilitybox.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.zero.utilitybox.common.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

/**
 * @ProjectName appBoxBackSystem
 * @Author: zeroJun
 * @Date: 2018/8/14 11:28
 * @Description:
 */
@Slf4j
public class InterceptorUtil {

    public static final int CLASS_NAME = 0;
    public static final int METHOD_NAME = 1;
    public static final int REQUEST_PARAM = 2;
    public static final int REQUEST_URI = 3;
    private static final int DATA_LENGTH = 4;

    /**
     * 获取解析出来的数据
     *
     * @param httpServletRequest httpServletRequest
     * @param handler            handler
     * @return String[]
     */
    private static String[] getResolveData(HttpServletRequest httpServletRequest, Object handler) {
        // 处理本次被请求的controller接口的对象
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getName();
        String requestURI = httpServletRequest.getRequestURI();

        // 解析请求参数，以此获取具体的参数key以及value
        StringBuilder requestParamBuilder = new StringBuilder();
        Map paramMap = httpServletRequest.getParameterMap();
        for (Object o : paramMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;

            // request这个参数的map，里面的value返回的其实是一个字符串数组
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            // 拼接出完整的请求参数
            requestParamBuilder.append(mapKey).append("=").append(mapValue);
        }

        String[] resolveData = new String[DATA_LENGTH];
        resolveData[CLASS_NAME] = className;
        resolveData[METHOD_NAME] = methodName;
        resolveData[REQUEST_PARAM] = requestParamBuilder.toString();
        resolveData[REQUEST_URI] = requestURI;

        return resolveData;
    }

    /**
     * 打印请求参数日志
     *
     * @param httpServletRequest httpServletRequest
     * @param handler            handler
     * @return String[]
     */
    public static String[] printRequestLog(HttpServletRequest httpServletRequest, Object handler) {
        String[] resolveData = getResolveData(httpServletRequest, handler);

        log.info("权限拦截器拦截到请求, className : {}, methodName : {}, param : {}, requestURI : {}", resolveData[CLASS_NAME], resolveData[METHOD_NAME], resolveData[REQUEST_PARAM],resolveData[REQUEST_URI]);

        return resolveData;
    }

    /**
     * 重置HttpServletResponse对象
     *
     * @param response response
     * @return HttpServletResponse
     */
    public static HttpServletResponse resetResponse(HttpServletResponse response) {
        // 重置HttpServletResponse对象
        response.reset();
        // 设置编码，否则会乱码，这是因为重置了Response对象，我们需要设置一些属性
        response.setCharacterEncoding("UTF-8");
        // 设置返回的数据类型，因为全部是json接口
        response.setContentType("application/json;charset=UTF-8");

        return response;
    }

    /**
     * 正确返回
     *
     * @param msg msg
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public static Map successResponseMap(String msg) {
        Map resultMap = Maps.newHashMap();
        resultMap.put("code", 0);
        resultMap.put("msg", msg);

        return resultMap;
    }

    /**
     * 错误提示返回
     *
     * @param msg msg
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public static Map errorResponseMap(String msg) {
        Map resultMap = Maps.newHashMap();
        resultMap.put("code", 1);
        resultMap.put("msg", msg);

        return resultMap;
    }

    /**
     * 错误提示返回
     *
     * @param response response
     * @param errorMsg errorMsg
     * @return boolean
     */
    public static boolean errorResponse(HttpServletResponse response, String errorMsg) {
        String jsonData = JsonUtil.obj2String(ServerResponse.createByErrorMsg(errorMsg));
        // 重置response并拿到输出流对象
        try (PrintWriter out = InterceptorUtil.resetResponse(response).getWriter()) {
            out.print(jsonData);
            out.flush();

            return false;
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }
}
