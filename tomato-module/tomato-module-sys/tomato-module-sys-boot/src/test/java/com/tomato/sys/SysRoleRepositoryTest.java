package com.tomato.sys;

import com.tomato.sys.domain.entity.SysRole;
import com.tomato.sys.infrastructure.repository.SysRoleRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/9
 */
@SpringBootTest
public class SysRoleRepositoryTest {

	@Resource
	private SysRoleRepository sysRoleRepository;

	@Test
	public void test() {
		System.out.println(LocalDateTime.now());
		SysRole sysRole = new SysRole();
		sysRole.setRoleName("test");
		sysRole.setRoleCode("test");
		sysRole.setRoleId(2L);
		sysRoleRepository.save(sysRole);
	}

}
