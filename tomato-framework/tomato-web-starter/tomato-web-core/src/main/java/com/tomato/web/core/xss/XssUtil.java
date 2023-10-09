package com.tomato.web.core.xss;

import org.owasp.validator.html.*;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * xss 工具类
 *
 * @author lizhifu
 * @since 2023/10/9
 */
public class XssUtil {
	private final AntiSamy antiSamy;
	private final String nbsp;
	private final String quot;
	public XssUtil(String resourceLocation) throws FileNotFoundException, PolicyException {
		URL url = ResourceUtils.getURL(resourceLocation);
		Policy policy = Policy.getInstance(url);
		this.antiSamy = new AntiSamy(policy);
		this.nbsp = cleanHtml("&nbsp;");
		this.quot = cleanHtml("\"");
	}
	public XssUtil() throws FileNotFoundException, PolicyException {
		this("classpath:antisamy/antisamy-anythinggoes.xml");
	}
	/**
	 * 清洗
	 * @param taintedHTML html
	 * @return 结果
	 */
	public String cleaning(String taintedHTML) {
		// 对转义的HTML特殊字符（<、>、"等）进行反转义，AntiSamy调用scan方法时会将特殊字符转义
		String cleanHtml = HtmlUtils.htmlUnescape(cleanHtml(taintedHTML));
		//AntiSamy会把“&nbsp;”转换成乱码，把双引号转换成"&quot;" 先将&nbsp;的乱码替换为空，双引号的乱码替换为双引号
		String temp = cleanHtml.replaceAll(this.nbsp, "");
		temp = temp.replaceAll(this.quot, "\"");
        return temp.replaceAll("\n", "");
	}
	private String cleanHtml(String taintedHtml) {
		try {
			// AntiSamy 清洗数据
            assert this.antiSamy != null;
            final CleanResults cleanResults = this.antiSamy.scan(taintedHtml);
			return cleanResults.getCleanHTML();
		} catch (ScanException | PolicyException e) {
			return taintedHtml;
		}
	}
}
