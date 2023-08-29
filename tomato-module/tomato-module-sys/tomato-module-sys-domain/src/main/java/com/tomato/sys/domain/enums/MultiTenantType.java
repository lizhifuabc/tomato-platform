package com.tomato.sys.domain.enums;

import com.tomato.common.enums.BaseEnum;

/**
 * 多租户模式
 *
 * @author lizhifu
 * @since 2023/6/12
 */
public enum MultiTenantType implements BaseEnum<String> {

	/**
	 * 租户字段隔离 所有租户共享同一个表,通过表中增加租户ID字段进行区分。 这种方式开发和维护比较简单,但是存在租户数据安全和隔离难保证的问题。
	 */
	COLUMN("COLUMN", "租户字段隔离"),
	/**
	 * 推荐:共享数据源,同schema,租户ID隔离 所有租户共享同一个数据源和schema,通过租户ID + 表命名策略区分不同租户的数据。 例如accounts表分为:
	 * accounts_tenant1 (租户1) accounts_tenant2 (租户2) 这种方式资源利用最高,迁移方便,但是SQL可能会比较复杂。
	 */
	TABLE("TABLE", "共享数据源,同schema,租户ID隔离"),
	/**
	 * 共享数据源,不同schema 所有租户共享一个数据源,但每个租户拥有独立的schema。
	 * 使用JPA时通过@Entity的schema属性指定租户的schema,这样不同租户的表就被隔离在不同的schema下。
	 * 这种方式资源利用率较高,但是迁移租户表时比较麻烦。
	 */
	SCHEMA("SCHEMA", "共享数据源,不同schema"),
	/**
	 * 不同数据源 为每个租户创建独立的数据源,使用JPA操作不同的数据源实现隔离。 这种方式实现简单,但是不太实际,因为租户数量较多时,创建和管理大量数据源会很麻烦。
	 */
	DATABASE("DATABASE", "不同数据源");

	MultiTenantType(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	private final String value;

	private final String desc;

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getValue() {
		return value;
	}

}
