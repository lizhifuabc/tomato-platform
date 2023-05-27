package com.tomato.reconciliation.dbinfo.internal.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.reconciliation.dbinfo.internal.domain.DbInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对账数据源信息
 *
 * @author lizhifu
 * @since 2023/5/27
 */
@Mapper
public interface DbInfoMapper extends BaseMapper<DbInfo,Long> {

}
