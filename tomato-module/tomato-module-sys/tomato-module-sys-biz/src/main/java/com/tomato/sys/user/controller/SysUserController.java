package com.tomato.sys.user.controller;

import com.tomato.domain.resp.Resp;
import org.springframework.web.bind.annotation.*;


/**
 * 系统用户相关
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@RestController
public class SysUserController {
    /**
     * 更新禁用/启用状态
     * @param id id
     * @return 成功
     */
    @GetMapping("/sys/user/update/disabled/{id}")
    public Resp updateDisableFlag(@PathVariable Long id) {
        return Resp.buildSuccess();
    }
}
