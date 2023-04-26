package com.tomato.web.core.util.servlet;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * 获取客户端真实IP
 *
 * @author lizhifu
 * @since 2022/12/20
 */
public class IpUtil {
    /**
     * 获取客户端真实IP
     * @param request request
     * @return ip
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip;
        // headers 数组
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        for(String header : headers){
            ip = request.getHeader(header);
            if(realIp(ip)){
                return getMultistageReverseProxyIp(ip);
            }
        }
        ip = request.getRemoteAddr();
        return getMultistageReverseProxyIp(ip);
    }
    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关<br>
     *
     * @param checkIp 被检测的IP
     * @return 是否未知
     */
    private static boolean realIp(String checkIp) {
        return StringUtils.hasText(checkIp) && !"unknown".equalsIgnoreCase(checkIp);
    }
    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    private static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (realIp(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }
}
