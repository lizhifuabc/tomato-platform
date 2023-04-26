package com.tomato.jpa.domain.service;


import com.tomato.common.entity.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * 可写服务
 *
 * @author lizhifu
 * @param <E>  实体
 * @param <ID> ID
 */
public interface JpaWriteableService<E extends Entity, ID extends Serializable> extends JpaBaseService<E, ID>{

    /**
     * 删除数据
     *
     * @param entity 数据对应实体
     */
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    /**
     * 批量全部删除
     */
    default void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }

    /**
     * 删除指定多个数据
     *
     * @param entities 数据对应实体集合
     */
    default void deleteAll(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    /**
     * 删除全部数据
     */
    default void deleteAll() {
        getRepository().deleteAll();
    }

    /**
     * 根据ID删除数据
     *
     * @param id 数据对应ID
     */
    default void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 保存数据
     *
     * @param domain 数据对应实体
     * @return 已保存数据
     */
    default E save(E domain) {
        return getRepository().save(domain);
    }

    /**
     * 批量保存
     *
     * @param entities 实体集合
     * @return 已经保存的实体集合
     */
    default <S extends E> List<S> saveAll(Iterable<S> entities) {
        return getRepository().saveAll(entities);
    }

    /**
     * 保存并且刷新
     *
     * @param entity 实体
     * @return 保存后实体
     */
    default E saveAndFlush(E entity) {
        return getRepository().saveAndFlush(entity);
    }

    /**
     * 保存或者更新
     *
     * @param entity 实体
     * @return 保存后实体
     */
    default E saveOrUpdate(E entity) {
        return saveAndFlush(entity);
    }

    /**
     * 刷新实体状态
     */
    default void flush() {
        getRepository().flush();
    }
}
