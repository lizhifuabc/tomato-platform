package com.tomato.notice.mapper;

import com.tomato.notice.entity.NoticeRuleEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * NoticeRuleMapper
 *
 * @author lizhifu
 * @since 2023/5/16
 */
@SpringBootTest
public class NoticeRuleMapperTest {
    @Resource
    NoticeRuleMapper noticeRuleMapper;

    @Test
    public void test() {
        NoticeRuleEntity noticeRuleEntity = new NoticeRuleEntity();
        Optional<NoticeRuleEntity> noticeRuleEntity1 = noticeRuleMapper.selectByPrimaryKey(1L);
        System.out.println(noticeRuleEntity1.get().getCreateTime());
    }

}
