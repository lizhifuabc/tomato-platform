package com.tomato.jpa.domain.service;


import com.tomato.common.entity.Entity;

import java.io.Serializable;

/**
 * 可读写服务
 *
 * @author lizhifu
 * @param <E>  实体
 * @param <ID> ID
 */
public abstract class AbstractService< E extends Entity, ID extends Serializable> implements BaseReadableService<E, ID>, BaseWriteableService<E, ID> {

}
