package com.tomato.web.core.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * IP 工具类
 *
 * @author lizhifu
 * @since 2023/7/19
 */
public class IpUtil {

	/**
	 * 本地 IP 地址
	 */
	public final static String LOCAL_IP = "127.0.0.1";

	/**
	 * 本地地址
	 */
	public final static String LOCAL_ADDRESS = "0:0:0:0:0:0:0:1";

	/**
	 * 获取当前 HttpServletRequest 请求
	 * @return HttpServletRequest 请求
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (null == attributes) {
			return null;
		}
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取当前 HttpServletRequest 请求 IP 地址
	 */
	private static final String[] HEADERS = { "x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR",
			"HTTP_X_FORWARDED", "HTTP_FORWARDED", "REMOTE_ADDR", "HTTP_VIA", "X-Real-IP" };

	/**
	 * 获取 request 请求 IP 地址
	 * @param request HttpServletRequest 请求
	 * @return IP 地址
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = null;
		for (String header : HEADERS) {
			String currentIp = request.getHeader(header);
			if (isNotUnknown(currentIp)) {
				ip = currentIp;
				break;
			}
		}
		if (null == ip) {
			ip = request.getRemoteAddr();
		}
		if (null == ip) {
			return "";
		}
		if (LOCAL_ADDRESS.equals(ip)) {
			return LOCAL_IP;
		}
		return getMultistageReverseProxyIp(ip);
	}

	/**
	 * 从多级反向代理中获得第一个非unknown IP地址
	 * @param ip 获得的IP地址
	 * @return 第一个非unknown IP地址
	 */
	private static String getMultistageReverseProxyIp(String ip) {
		// 多级反向代理检测
		String delimiter = ",";
		if (ip != null && ip.indexOf(delimiter) > 0) {
			String[] ips = ip.trim().split(delimiter);
			for (String subIp : ips) {
				if (isNotUnknown(subIp)) {
					ip = subIp;
					break;
				}
			}
		}
		return ip;
	}

	/**
	 * 检测IP地址是否未知，多用于反向代理的时候
	 * @param checkIp 检测IP地址
	 * @return 是否未知
	 */
	private static boolean isNotUnknown(String checkIp) {
		return StringUtils.hasLength(checkIp) && !"unknown".equalsIgnoreCase(checkIp);
	}

}
