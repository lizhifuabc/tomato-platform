package com.tomato.gen.dao;

import com.tomato.gen.domain.entity.GenFieldTypeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字段类型管理
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@Mapper
public interface GenFieldTypeDao {
    /**
     * 查询所有
     * @return 字段类型列表
     */
    List<GenFieldTypeEntity> selectAll();
}
