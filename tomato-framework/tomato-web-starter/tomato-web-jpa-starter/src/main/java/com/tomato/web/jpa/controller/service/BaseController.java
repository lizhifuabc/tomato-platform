package com.tomato.web.jpa.controller.service;

import com.tomato.common.domain.entity.Entity;
import com.tomato.common.domain.resp.Resp;
import com.tomato.web.core.controller.Controller;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseControllerService
 *
 * @author lizhifu
 * @since 2023/4/26
 */
interface BaseController extends Controller {

	/**
	 * 数据分页对象转换为统一响应实体
	 * @param pages 分页查询结果 {@link Page}
	 * @param <E> {@link Entity} 子类型
	 * @return {@link Resp} Map
	 */
	default <E extends Entity> Resp<Map<String, Object>> result(Page<E> pages) {
		return Resp.of(getPageInfoMap(pages));
	}

	/**
	 * Page 对象转换为 Map
	 * @param pages 分页查询结果 {@link Page}
	 * @param <E> {@link Entity} 子类型
	 * @return Map
	 */
	default <E extends Entity> Map<String, Object> getPageInfoMap(Page<E> pages) {
		return getPageInfoMap(pages.getContent(), pages.getTotalPages(), pages.getTotalElements());
	}

	/**
	 * Page 对象转换为 Map
	 * @param content 数据实体 List
	 * @param totalPages 分页总页数
	 * @param totalElements 总的数据条目
	 * @param <E> {@link Entity} 子类型
	 * @return Map
	 */
	default <E extends Entity> Map<String, Object> getPageInfoMap(List<E> content, int totalPages, long totalElements) {
		Map<String, Object> result = new HashMap<>(8);
		result.put("content", content);
		result.put("totalPages", totalPages);
		result.put("totalElements", totalElements);
		return result;
	}

}
