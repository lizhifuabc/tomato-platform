package com.tomato.web.core.xss;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Xss 请求参数包装器
 *
 * @author lizhifu
 * @since 2023/10/9
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private final XssUtil xssUtil;
	public XssHttpServletRequestWrapper(HttpServletRequest request,XssUtil xssUtil) {
		super(request);
		this.xssUtil = xssUtil;
	}
	/**
	 * 过滤请求头
	 *
	 * @param name 参数名
	 * @return 参数值
	 */
	@Override
	public String getHeader(String name) {
		String header = super.getHeader(name);
		// 如果Header为空，则直接返回，否则进行清洗
		return StringUtils.isBlank(header) ? header : cleaning(header);
	}

	@Override
	public String getParameter(String name) {
		String parameter = super.getParameter(name);
		// 如果parameter为空，则直接返回，否则进行清洗
		return StringUtils.isBlank(parameter) ? parameter : cleaning(parameter);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] parameterValues = super.getParameterValues(name);
		if (ArrayUtils.isNotEmpty(parameterValues)) {
			return cleaning(parameterValues);
		}
		return super.getParameterValues(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap = super.getParameterMap();
		return parameterMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> cleaning(entry.getValue())));
	}
	/**
	 * 使用AntiSamy清洗数据
	 *
	 * @param value 需要清洗的数据
	 * @return 清洗后的数据
	 */
	private String cleaning(String value) {
		return xssUtil.cleaning(value);
	}

	private String[] cleaning(String[] parameters) {
		List<String> cleanParameters = Arrays.stream(parameters).map(xssUtil::cleaning).collect(Collectors.toList());
		String[] results = new String[cleanParameters.size()];
		return cleanParameters.toArray(results);
	}
}
