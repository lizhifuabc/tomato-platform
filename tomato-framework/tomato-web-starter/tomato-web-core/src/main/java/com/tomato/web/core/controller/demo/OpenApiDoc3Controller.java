package com.tomato.web.core.controller.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * spring boot Open Api Doc 3 demo
 *
 * @author lizhifu
 * @since 2024/1/11
 */
@Tag(name = "测试接口", description = "定义测试接口")
@RestController
@RequestMapping("/openApiDoc3")
public class OpenApiDoc3Controller {
	@Operation(summary = "get测试接口", description = "返回id")
	@Parameter(name = "id", description = "参数ID", example = "123456")
	@ApiResponse(responseCode = "403", description = "无权限")
	@GetMapping("/get")
	public Map<String, Object> get(@Parameter(description = "id") String id) {
		Map<String, Object> res = new HashMap<>(1);
		res.put("id", id);
		return res;
	}
}
