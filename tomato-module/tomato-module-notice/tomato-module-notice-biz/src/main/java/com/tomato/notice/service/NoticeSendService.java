package com.tomato.notice.service;

import com.tomato.notice.domain.req.NoticeCreateReq;
import lombok.extern.slf4j.Slf4j;
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

    public NoticeSendService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> send(NoticeCreateReq noticeCreateReq){
        // 请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
        // 接收成功：HTTP应答状态码需返回200或204，无需返回应答报文。
        // 接收失败：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文，格式如下：
        //        {
        //            "code": "FAIL",
        //                "message": "失败"
        //        }
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                noticeCreateReq.getNoticeUrl(),
                noticeCreateReq.getNoticeParam(), String.class);
        return responseEntity;
    }
}