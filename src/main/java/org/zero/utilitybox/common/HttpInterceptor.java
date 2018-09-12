package org.zero.utilitybox.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.zero.utilitybox.util.InterceptorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: permission-system
 * @description: Http请求前后监听工具
 * @author: 01
 * @create: 2018-09-12 22:23
 **/
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private final static String START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        InterceptorUtil.printRequestLog(request, handler);
        request.setAttribute(START_TIME, System.currentTimeMillis());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI();
        log.info("request completion. url : {}, cost : {}ms",
                url, System.currentTimeMillis() - (long) request.getAttribute(START_TIME));
    }
}
