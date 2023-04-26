package com.tomato.jpa.domain.service;

import com.tomato.common.entity.Entity;
import com.tomato.jpa.domain.repository.BaseJpaRepository;

import java.io.Serializable;

/**
 * 基础服务
 *
 * @author lizhifu
 * @since 2023/4/26
 */
interface BaseService<E extends Entity, ID extends Serializable> {
    /**
     * 获取Repository
     *
     * @return {@link BaseJpaRepository}
     */
    BaseJpaRepository<E, ID> getRepository();
}
