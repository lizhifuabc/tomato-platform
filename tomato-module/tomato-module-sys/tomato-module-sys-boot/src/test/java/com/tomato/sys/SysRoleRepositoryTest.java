package com.tomato.sys;

import com.tomato.sys.infrastructure.entity.SysPermission;
import com.tomato.sys.infrastructure.entity.SysRole;
import com.tomato.sys.infrastructure.repository.SysRoleRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.Set;

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
    public void test(){
        System.out.println(LocalDateTime.now());

        SysPermission sysPermission = new SysPermission();
        sysPermission.setPermissionName("test");
        sysPermission.setPermissionCode("test");
        sysPermission.setPermissionId(1L);

        Set<SysPermission> permissions = Set.of(sysPermission);

        SysRole sysRole = new SysRole();
        sysRole.setRoleName("test");
        sysRole.setRoleCode("test");
//        sysRole.setPermissions(permissions);
        sysRole.setRoleId(2L);

        sysRoleRepository.save(sysRole);
    }
}
