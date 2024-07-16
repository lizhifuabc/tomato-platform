package com.tomato.gen.mp;

import cn.mybatis.mp.generator.FastGenerator;
import cn.mybatis.mp.generator.config.GeneratorConfig;

/**
 * https://mybatis-mp.cn/zh-CN/function/core/codeAutoCreate.html
 *
 * @author lizhifu
 * @since 2024/7/15
 */
public class Gen {
	public static void main(String[] args) {
		new FastGenerator(new GeneratorConfig(
				"jdbc:mysql://127.0.0.1:3306/tomato-sys",
				"root",
				"12345678")
				.basePackage("com.test")
				.tableConfig(tableConfig -> {
					tableConfig.tablePrefixs("t_");
					tableConfig.includeTable("t_serial_number", "t_serial_number_record");
				})//根包路径
				.entityConfig(entityConfig -> {
					entityConfig.lombok(true);
					entityConfig.packageName("entity");
				})
				.actionConfig(actionConfig->{
					actionConfig.enable(true);
				})
		).create();
	}
}
