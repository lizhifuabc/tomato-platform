package com.tomato.jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 基础 Repository
 * <p>JpaSpecificationExecutor：用于实现动态查询功能。</p>
 * <p>JpaRepository：用于实现基本的 CRUD 操作。</p>
 * @param <E> 实体
 * @param <ID> 主键
 * @author lizhifu
 */
@NoRepositoryBean // 表示该接口不是一个 JPA 仓库（Repository）接口，不会被 Spring Data JPA 自动实现
public interface BaseJpaRepository<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

}
