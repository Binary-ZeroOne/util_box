package org.zero.utilitybox.aspect;

import com.tiannuoiot.settlement.common.exception.BusinessException;
import com.tiannuoiot.settlement.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 当get的请求参数为下划线命名且无法发送json请求时通过该切面类转换参数
 *
 * @author 01
 * @date 2020-08-18
 */
@Slf4j
@Aspect
@Component
public class GetMappingAspect {

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object handleGetParam(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new BusinessException("request对象为空");
        }
        HttpServletRequest request = attributes.getRequest();

        Object[] args = joinPoint.getArgs();
        Class<?> argCls = args[0].getClass();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String json = parameterMapToJson(parameterMap);
        args[0] = JsonUtil.json2Obj(json, argCls);

        return joinPoint.proceed(args);
    }

    private String parameterMapToJson(Map<String, String[]> parameterMap) {
        StringBuilder sb = new StringBuilder("{");
        parameterMap.forEach((k, v) -> {
            sb.append("\"").append(k).append("\":");
            if (!v[0].contains(",")) {
                sb.append("\"").append(v[0]).append("\"");
            } else {
                sb.append("[");
                String[] items = v[0].split(",");
                for (String item : items) {
                    sb.append("\"").append(item).append("\"").append(",");
                }
                sb.deleteCharAt(sb.length() - 1).append("]");
            }
            sb.append(",");
        });
        sb.deleteCharAt(sb.length() - 1).append("}");

        return sb.toString();
    }
}
