package com.tomato.notice.service.manager;

import com.tomato.jackson.utils.JacksonUtils;
import com.tomato.notice.dto.req.NoticeCreateReq;
import com.tomato.notice.entity.NoticeRecordEntity;
import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import com.tomato.notice.mapper.NoticeRecordHistoryMapper;
import com.tomato.notice.mapper.NoticeRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * 商户通知记录
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@Service
@Slf4j
public class NoticeRecordManager {
    private final NoticeRecordMapper noticeRecordMapper;
    private final NoticeRecordHistoryMapper noticeRecordHistoryMapper;
    public NoticeRecordManager(NoticeRecordMapper noticeRecordMapper, NoticeRecordHistoryMapper noticeRecordHistoryMapper) {
        this.noticeRecordMapper = noticeRecordMapper;
        this.noticeRecordHistoryMapper = noticeRecordHistoryMapper;
    }
    /**
     * 创建通知记录
     * @param noticeCreateReq 通知记录
     * @return NoticeRecordEntity 通知记录
     */
    public NoticeRecordEntity createNotice(NoticeCreateReq noticeCreateReq){
        // 创建通知记录
        NoticeRecordEntity noticeRecordEntity = new NoticeRecordEntity();
        BeanUtils.copyProperties(noticeCreateReq, noticeRecordEntity);
        noticeRecordEntity.setHttpMethod(noticeCreateReq.getHttpMethod().toString());
        noticeRecordEntity.setNoticeParam(JacksonUtils.toJson(noticeCreateReq.getNoticeParam()));
        noticeRecordMapper.insertSelective(noticeRecordEntity);
        return noticeRecordEntity;
    }

    /**
     * 创建通知记录历史
     * @param noticeRecordEntity 通知记录
     * @return NoticeRecordHistoryEntity 通知记录历史
     */
    public NoticeRecordHistoryEntity createNoticeHis(NoticeRecordEntity noticeRecordEntity){
        NoticeRecordHistoryEntity noticeRecordHistoryEntity = new NoticeRecordHistoryEntity();
        noticeRecordHistoryEntity.setNoticeRecordId(noticeRecordEntity.getId());
        noticeRecordHistoryEntity.setCreateTime(LocalDateTime.now());
        noticeRecordHistoryMapper.insertSelective(noticeRecordHistoryEntity);
        return noticeRecordHistoryEntity;
    }
    /**
     * 通知结果
     * @param noticeRecordHistoryEntity 通知记录历史
     */
    @Transactional(rollbackFor = Exception.class)
    public void noticeResult(NoticeRecordHistoryEntity noticeRecordHistoryEntity){
        // 更新通知记录
        noticeRecordMapper.updateNoticeResult(noticeRecordHistoryEntity.getNoticeRecordId(),noticeRecordHistoryEntity.getState(),noticeRecordHistoryEntity.getNoticeResult());
        // 更新通知记录历史
        noticeRecordHistoryEntity.setCompleteTime(LocalDateTime.now());
        noticeRecordHistoryEntity.setCostTime(noticeRecordHistoryEntity.getCreateTime().until(noticeRecordHistoryEntity.getCompleteTime(), ChronoUnit.MILLIS));
        noticeRecordHistoryMapper.updateByPrimaryKeySelective(noticeRecordHistoryEntity);
    }
    /**
     * 根据 ID 查询
     * @param id id
     * @return NoticeRecordEntity
     */
    public NoticeRecordEntity selectById(Long id){
        return noticeRecordMapper.selectById(id);
    }

    /**
     * 根据商户号和商户订单号查询
     * @param merchantNo 商户号
     * @param merchantOrderNo 商户订单号
     * @return NoticeRecordEntity
     */
    public Optional<NoticeRecordEntity> selectByMerchant(String merchantNo, String merchantOrderNo) {
        return Optional.ofNullable(noticeRecordMapper.selectByMerchant(merchantNo, merchantOrderNo));
    }
}
