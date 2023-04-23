package com.tomato.notice.service;

import com.tomato.notice.constant.NoticeRecordState;
import com.tomato.notice.domain.bo.NoticeDelayBO;
import com.tomato.notice.domain.entity.NoticeRecordEntity;
import com.tomato.notice.manager.NoticeRecordManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 发送http请求
 *
 * @author lizhifu
 * @since 2023/1/6
 */
@Slf4j
@Service
public class NoticeSendService {
    private final RestTemplate restTemplate;
    private final NoticeRecordManager noticeRecordManager;
    private final NoticeRabbitService noticeRabbitService;

    public NoticeSendService(RestTemplate restTemplate, NoticeRecordManager noticeRecordManager, NoticeRabbitService noticeRabbitService) {
        this.restTemplate = restTemplate;
        this.noticeRecordManager = noticeRecordManager;
        this.noticeRabbitService = noticeRabbitService;
    }

    public void send(Long id){
        NoticeRecordEntity noticeRecordEntity = noticeRecordManager.selectById(id);
        // 请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
        // 接收成功：HTTP应答状态码需返回200或204，无需返回应答报文。
        // 接收失败：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文，格式如下：
        //        {
        //            "code": "FAIL",
        //                "message": "失败"
        //        }
        HttpEntity<String> entity = new HttpEntity<>(noticeRecordEntity.getNoticeParam(), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                noticeRecordEntity.getNoticeUrl(),
                entity,
                String.class);
        if(responseEntity.getStatusCode().is2xxSuccessful() && NoticeRecordState.SUCCESS.equalsIgnoreCase(responseEntity.getBody())){
            noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_SUCCESS,responseEntity.getBody());
        }else {
            noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_FAIL,responseEntity.getBody());
            // TODO 重发通知到MQ
            // 通知次数 >= 最大通知次数时
            if(noticeRecordEntity.getNoticeCount() >= noticeRecordEntity.getNoticeCountLimit()){
                return;
            }
            NoticeDelayBO noticeDelayBO = new NoticeDelayBO();
            noticeDelayBO.setNoticeCount(noticeRecordEntity.getNoticeCount());
            noticeDelayBO.setId(noticeRecordEntity.getId());
            noticeRabbitService.delayNotice(noticeDelayBO);
        }
    }
}