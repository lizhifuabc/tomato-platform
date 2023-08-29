package com.tomato.mybatis.paginate;

import com.tomato.mybatis.domain.Sort;

import java.io.Serializable;

/**
 * 分页
 *
 * @author lizhifu
 */
public class Page implements Serializable {

	public static final int NO_ROW_OFFSET = 0;

	public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;

	private final int offset;

	private final int limit;

	/**
	 * 强制排序
	 */
	private final Sort sort;

	public Page(int offset, int limit, Sort sort) {
		this.offset = offset;
		this.limit = limit;
		this.sort = sort;
	}

	public int getOffset() {
		return offset;
	}

	public int getLimit() {
		return limit;
	}

	public Sort getSort() {
		return sort;
	}

}
