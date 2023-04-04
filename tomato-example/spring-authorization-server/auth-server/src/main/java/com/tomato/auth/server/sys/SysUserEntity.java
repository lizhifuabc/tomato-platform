package com.tomato.auth.server.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* User
* @author lizhifu
* @since 2023/4/4
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserEntity{
    private Long id;

    private String username;

    private String password;
}
