package com.tomato.notice.mapper;

import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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
        // 更新相关测试
        NoticeRecordHistoryEntity update = new NoticeRecordHistoryEntity();
        update.setId(6L);
        update.setCreateTime(LocalDateTime.now());
        update.setNoticeRecordId(1L);
        System.out.println("更新:" + noticeRecordHistoryMapper.updateByPrimaryKey(update));

        NoticeRecordHistoryEntity update2 = new NoticeRecordHistoryEntity();
        update2.setId(7L);
        update2.setCreateTime(LocalDateTime.now());
        update2.setNoticeRecordId(1L);
        System.out.println("更新:" + noticeRecordHistoryMapper.updateByPrimaryKeySelective(update2));


        // 删除相关测试
        System.out.println("删除:" + noticeRecordHistoryMapper.deleteByPrimaryKey(2L));

        NoticeRecordHistoryEntity delete = new NoticeRecordHistoryEntity();
        delete.setNoticeRecordId(3L);
        System.out.println("删除:" + noticeRecordHistoryMapper.deleteByCriteria(delete));

        System.out.println("删除:" + noticeRecordHistoryMapper.logicDeleteByPrimaryKey(4L));

        // 统计相关测试
        System.out.println("统计:"+noticeRecordHistoryMapper.count());

        NoticeRecordHistoryEntity count = new NoticeRecordHistoryEntity();
        count.setNoticeRecordId(3L);
        System.out.println("统计:"+noticeRecordHistoryMapper.countByCriteria(count));

        // 插入相关测试
        NoticeRecordHistoryEntity insert1 = new NoticeRecordHistoryEntity();
        insert1.setNoticeRecordId(1L);
        insert1.setCreateTime(LocalDateTime.now());
        noticeRecordHistoryMapper.insertSelective(insert1);
        System.out.println("返回ID："+insert1.getId());

        NoticeRecordHistoryEntity insert = new NoticeRecordHistoryEntity();
        insert.setNoticeRecordId(1L);
        insert.setCreateTime(LocalDateTime.now());
        noticeRecordHistoryMapper.insert(insert);
        System.out.println("返回ID："+insert.getId());

        NoticeRecordHistoryEntity batch1 = new NoticeRecordHistoryEntity();
        batch1.setNoticeRecordId(1L);
        batch1.setCreateTime(LocalDateTime.now());

        NoticeRecordHistoryEntity batch2 = new NoticeRecordHistoryEntity();
        batch2.setNoticeRecordId(1L);
        batch2.setCreateTime(LocalDateTime.now());
        noticeRecordHistoryMapper.batchInsert(List.of(batch1,batch2));


        // 查询相关测试
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
