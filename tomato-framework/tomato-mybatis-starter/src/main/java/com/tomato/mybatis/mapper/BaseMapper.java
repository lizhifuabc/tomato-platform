package com.tomato.mybatis.mapper;


/**
 * 基础mapper
 *
 * @author lizhifu
 * @since 2023/3/23
 */
public interface BaseMapper<T> {
    /**
     * 插入
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    T selectByPrimaryKey(Long id);

    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);
}
