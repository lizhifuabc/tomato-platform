package com.tomato.gen.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * GenTableService
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@SpringBootTest
public class GenTableServiceTest {

	@Resource
	GenTableService genTableService;

	@Test
	public void test() {
		String tableName = "t_gen_field_type";
		genTableService.genCode(tableName);
	}

}
