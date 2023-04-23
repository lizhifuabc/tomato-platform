package com.tomato.notice.manager;

import com.tomato.jackson.utils.JacksonUtils;
import com.tomato.notice.mapper.NoticeRecordMapper;
import com.tomato.notice.domain.entity.NoticeRecordEntity;
import com.tomato.notice.domain.req.NoticeCreateReq;
import com.tomato.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

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
    public NoticeRecordManager(NoticeRecordMapper noticeRecordMapper) {
        this.noticeRecordMapper = noticeRecordMapper;
    }

    public NoticeRecordEntity createNotice(NoticeCreateReq noticeCreateReq){
        NoticeRecordEntity noticeRecordEntity = BeanUtil.copy(noticeCreateReq,NoticeRecordEntity.class);
        noticeRecordEntity.setNoticeParam(JacksonUtils.toJson(noticeCreateReq.getNoticeParam()));
        noticeRecordMapper.insert(noticeRecordEntity);
        return noticeRecordEntity;
    }
    public void noticeResult(Long id, Byte state, String noticeResult){
        noticeRecordMapper.updateNoticeResult(id,state,noticeResult);
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
