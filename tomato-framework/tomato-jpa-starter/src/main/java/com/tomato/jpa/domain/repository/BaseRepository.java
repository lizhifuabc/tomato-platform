package com.tomato.jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 基础Repository
 *
 * @author lizhifu
 */
@NoRepositoryBean // 表示该接口不是一个 JPA 仓库（Repository）接口，不会被 Spring Data JPA 自动实现
public interface BaseRepository<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

}
