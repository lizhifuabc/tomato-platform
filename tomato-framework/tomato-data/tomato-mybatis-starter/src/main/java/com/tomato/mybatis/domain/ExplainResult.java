package com.tomato.mybatis.domain;

import lombok.Data;

/**
 * ExplainResult
 *
 * @author lizhifu
 * @since 2023/7/31
 */
@Data
public class ExplainResult {

	private String id;

	private String selectType;

	private String table;

	private String partitions;

	private String type;

	private String possibleKeys;

	private String key;

	private String keyLen;

	private String ref;

	private String rows;

	private String filtered;

	private String extra;

}
