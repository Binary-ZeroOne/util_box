package org.zero.utilitybox.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName appBoxBackSystem
 * @Author: zeroJun
 * @Date: 2018/8/14 11:45
 * @Description:
 */
@Slf4j
public class CookieUtil {

    private final static String COOKIE_NAME = "SESSION_ID";

    /**
     * 读取cookie
     *
     * @param request request
     * @return String
     */
    public static String getLoginCookieValue(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName: {}, cookieValue: {}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName: {}, cookieValue: {}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }
}
