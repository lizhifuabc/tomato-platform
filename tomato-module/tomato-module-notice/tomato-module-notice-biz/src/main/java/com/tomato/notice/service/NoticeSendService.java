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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

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
    private final WebClient webClient;
    public NoticeSendService(RestTemplate restTemplate, NoticeRecordManager noticeRecordManager, NoticeRabbitService noticeRabbitService, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.noticeRecordManager = noticeRecordManager;
        this.noticeRabbitService = noticeRabbitService;
        this.webClient = webClient;
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
        boolean success;
        String body;
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    noticeRecordEntity.getNoticeUrl(),
                    entity,
                    String.class);
            success = responseEntity.getStatusCode().is2xxSuccessful() && NoticeRecordState.SUCCESS.equalsIgnoreCase(responseEntity.getBody());
            body = responseEntity.getBody();
        }catch (Exception e){
            body = e.getMessage();
            success = false;
        }
        if(success){
            noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_SUCCESS,body);
        }else {
            noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_FAIL,body);
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
    public void sendAsync(Long id){
        NoticeRecordEntity noticeRecordEntity = noticeRecordManager.selectById(id);
        // webClient 发送post请求，并对错误进行处理
        // TODO webClient 重试方式进行优化??rabbitmq延迟队列
        webClient.post()
                .uri(noticeRecordEntity.getNoticeUrl())
                .body(BodyInserters.fromValue(noticeRecordEntity.getNoticeParam()))
                // 获取响应体
                .retrieve()
                //响应数据类型转换
                .bodyToMono(String.class)
                // 异步处理:将后续的操作切换到一个由 boundedElastic 线程池维护的线程中执行
                // TODO boundedElastic 线程池使用的是有界的队列，可以防止线程数无限增长，从而避免资源耗尽的风险。
                //  当任务数量超过队列容量时，会触发线程池的饱和策略，比如说抛出异常或者丢弃任务。
                .publishOn(Schedulers.boundedElastic())
                .doOnError(throwable -> {
                    noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_FAIL,throwable.getMessage());
                    // 通知次数 >= 最大通知次数时
                    if(noticeRecordEntity.getNoticeCount() >= noticeRecordEntity.getNoticeCountLimit()){
                        return;
                    }
                    NoticeDelayBO noticeDelayBO = new NoticeDelayBO();
                    noticeDelayBO.setNoticeCount(noticeRecordEntity.getNoticeCount());
                    noticeDelayBO.setId(noticeRecordEntity.getId());
                    noticeRabbitService.delayNotice(noticeDelayBO);
                }).subscribe(result -> {
                    noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_SUCCESS,result);
                });
    }
}