package com.tomato.notice.mapper;

import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

/**
 * NoticeRecordHistoryMapper
 *
 * @author lizhifu
 * @since 2023/5/18
 */
@SpringBootTest
public class NoticeRecordHistoryMapperTest {
    @Resource
    NoticeRecordHistoryMapper noticeRecordHistoryMapper;

    @Test
    public void test() {
        noticeRecordHistoryMapper.selectByPrimaryKey(2L).ifPresent(System.out::println);

        noticeRecordHistoryMapper.selectByPrimaryKeyIn(List.of(1L,2L)).forEach(System.out::println);

        noticeRecordHistoryMapper.selectAll("id desc").forEach(System.out::println);

        NoticeRecordHistoryEntity entity = new NoticeRecordHistoryEntity();

        entity.setId(5L);
        noticeRecordHistoryMapper.selectOneByCriteria(entity).ifPresent(System.out::println);

        entity.setNoticeRecordId(3L);
        noticeRecordHistoryMapper.selectByCriteria("id desc",entity).forEach(System.out::println);
    }
}
