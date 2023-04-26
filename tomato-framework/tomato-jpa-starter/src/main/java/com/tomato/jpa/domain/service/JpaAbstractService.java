package com.tomato.jpa.domain.service;

import com.tomato.jpa.domain.entity.base.JpaEntity;

import java.io.Serializable;

/**
 * 可读写服务
 *
 * @author lizhifu
 * @param <E>  实体
 * @param <ID> ID
 */
public abstract class JpaAbstractService< E extends JpaEntity, ID extends Serializable> implements JpaReadableService<E, ID>, JpaWriteableService<E, ID> {

}
