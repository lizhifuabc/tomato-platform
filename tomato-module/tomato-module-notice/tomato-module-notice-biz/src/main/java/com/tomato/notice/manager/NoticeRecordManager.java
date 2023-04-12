package com.tomato.notice.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.domain.core.exception.BusinessException;
import com.tomato.notice.dao.NoticeRecordDao;
import com.tomato.notice.domain.entity.NoticeRecordEntity;
import com.tomato.notice.domain.req.NoticeCreateReq;
import com.tomato.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 商户通知记录
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@Service
@Slf4j
public class NoticeRecordManager {
    private final NoticeRecordDao noticeRecordDao;
    private final ObjectMapper objectMapper;
    public NoticeRecordManager(NoticeRecordDao noticeRecordDao, ObjectMapper objectMapper) {
        this.noticeRecordDao = noticeRecordDao;
        this.objectMapper = objectMapper;
    }

    public NoticeRecordEntity createNotice(NoticeCreateReq noticeCreateReq){
        NoticeRecordEntity noticeRecordEntity = BeanUtil.copy(noticeCreateReq,NoticeRecordEntity.class);
        try {
            noticeRecordEntity.setNoticeParam(objectMapper.writeValueAsString(noticeCreateReq.getNoticeParam()));
        } catch (JsonProcessingException e) {
            log.error("noticeCreateReq:{},通知参数格式错误",noticeCreateReq,e);
            throw new BusinessException("通知参数格式错误");
        }
        noticeRecordDao.insert(noticeRecordEntity);
        return noticeRecordEntity;
    }
    public void noticeResult(Long id, Byte state, String noticeResult){
        noticeRecordDao.updateNoticeResult(id,state,noticeResult);
    }
    /**
     * 根据 ID 查询
     * @param id id
     * @return
     */
    public NoticeRecordEntity selectById(Long id){
        return noticeRecordDao.selectById(id);
    }
}
