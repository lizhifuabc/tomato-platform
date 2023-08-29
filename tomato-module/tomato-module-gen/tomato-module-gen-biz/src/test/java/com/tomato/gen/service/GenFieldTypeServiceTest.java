package com.tomato.gen.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * GenFieldTypeService
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@SpringBootTest
public class GenFieldTypeServiceTest {

	@Resource
	GenFieldTypeService genFieldTypeService;

	@Test
	public void test() {
		System.out.println(genFieldTypeService.getByDataType("varchar").getAttrType());
	}

}
