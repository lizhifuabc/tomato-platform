package com.tomato.gen.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * EntityVariableService
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@SpringBootTest
public class EntityVariableServiceTest {

	@Resource
	EntityVariableService entityVariableService;

	@Test
	public void test() {
		String tableName = "t_gen_field_type";
		entityVariableService.getInjectVariablesMap(tableName);
	}

}
