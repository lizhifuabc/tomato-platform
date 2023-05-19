package com.tomato.notice.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.mybatis.mapper.mapper.PageMapper;
import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知记录历史
 *
 * @author lizhifu
 * @since 2023/4/24
 */
@Mapper
public interface NoticeRecordHistoryMapper extends BaseMapper<NoticeRecordHistoryEntity, Long> {

}
