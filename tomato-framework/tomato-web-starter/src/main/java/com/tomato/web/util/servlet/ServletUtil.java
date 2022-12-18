package com.tomato.web.util.servlet;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Map;

/**
 * Servlet
 *
 * @author lizhifu
 * @since 2022/12/18
 */
public class ServletUtil {
    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    public static Map<String, String[]> getParams(ServletRequest request) {
        final Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    /**
     * 获取客户端IP
     *
     * <p>
     * 默认检测的Header:
     *
     * <pre>
     * 1、X-Forwarded-For
     * 2、X-Real-IP
     * 3、Proxy-Client-IP
     * 4、WL-Proxy-Client-IP
     * </pre>
     *
     * <p>
     * otherHeaderNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request          请求对象{@link HttpServletRequest}
     * @return IP地址
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip;
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        for (String header : headers) {
            ip = request.getHeader(header);
            if (!isUnknown(ip)) {
                return getProxyIp(ip);
            }
        }
        ip = request.getRemoteAddr();
        return getProxyIp(ip);
    }
    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     * @since 4.4.1
     */
    private static String getProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            for (String subIp : ip.trim().split(",")) {
                if (!isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }
    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关<br>
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     * @since 5.2.6
     */
    private static boolean isUnknown(String checkString) {
        return ObjectUtils.isEmpty(checkString) || "unknown".equalsIgnoreCase(checkString);
    }
}
