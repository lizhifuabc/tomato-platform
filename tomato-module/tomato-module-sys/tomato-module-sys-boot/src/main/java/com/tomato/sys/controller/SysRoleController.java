package com.tomato.sys.controller;

import com.tomato.common.resp.Resp;
import com.tomato.sys.form.SysRoleAddForm;
import com.tomato.sys.form.SysRoleUpdateForm;
import com.tomato.sys.vo.SysRoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色
 *
 * @author lizhifu
 * @since 2023/4/11
 */
@RestController
@Tag(name = "角色", description = "角色相关API")
public class SysRoleController {

	@Operation(summary = "添加角色")
	@PostMapping("/role/add")
	public Resp<Void> addRole(@Valid @RequestBody SysRoleAddForm sysRoleAddForm) {
		return Resp.buildSuccess();
	}

	@Operation(summary = "删除角色")
	@GetMapping("/role/delete/{roleId}")
	public Resp<Void> deleteRole(@PathVariable Long roleId) {
		return Resp.buildSuccess();
	}

	@Operation(summary = "更新角色")
	@PostMapping("/role/update")
	public Resp<Void> updateRole(@Valid @RequestBody SysRoleUpdateForm roleUpdateDTO) {
		return Resp.buildSuccess();
	}

	@Operation(summary = "获取角色数据")
	@GetMapping("/role/get/{roleId}")
	public Resp<SysRoleVO> getRole(@PathVariable("roleId") Long roleId) {
		return Resp.of(new SysRoleVO());
	}

	@Operation(summary = "获取所有角色")
	@GetMapping("/role/getAll")
	public Resp<List<SysRoleVO>> getAllRole() {
		return Resp.of(List.of(new SysRoleVO()));
	}

}
