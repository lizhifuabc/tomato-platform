package com.tomato.notice.manager;

import com.tomato.notice.dao.NoticeRecordDao;
import com.tomato.notice.domain.entity.NoticeRecordEntity;
import com.tomato.notice.domain.req.NoticeCreateReq;
import com.tomato.web.util.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * 商户通知记录
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@Service
public class NoticeRecordManager {
    private final NoticeRecordDao noticeRecordDao;

    public NoticeRecordManager(NoticeRecordDao noticeRecordDao) {
        this.noticeRecordDao = noticeRecordDao;
    }

    public void createNotice(NoticeCreateReq noticeCreateReq){
        NoticeRecordEntity noticeRecordEntity = BeanUtil.copy(noticeCreateReq,NoticeRecordEntity.class);
        noticeRecordDao.insert(noticeRecordEntity);
    }
}
