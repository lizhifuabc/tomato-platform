package com.tomato.pay.common.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 回调请求
 *
 * @author lizhifu
 * @since 2023/7/27
 */
@Data
public class CallBackReq {

	/**
	 * 请求体
	 */
	private Map<String, Object> body;

	/**
	 * 请求头
	 */
	private Map<String, List<String>> headers;

	/**
	 * 其他附加属性
	 */
	private Map<String, Object> attr;

}
