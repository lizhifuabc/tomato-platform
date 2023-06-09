package com.tomato.sys.infrastructure.repository;


import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.sys.domain.entity.SysToken;

/**
 * token repository
 *
 * @author lizhifu
 * @since 2023/6/9
 */
public interface SysTokenRepository extends BaseJpaRepository<SysToken, Long> {

}
