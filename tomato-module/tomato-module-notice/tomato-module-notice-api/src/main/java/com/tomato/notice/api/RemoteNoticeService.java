package com.tomato.notice.api;

import com.tomato.common.resp.Resp;
import com.tomato.module.common.constants.ServiceNameConstants;
import com.tomato.notice.api.fallback.RemoteNoticeServiceFallback;
import com.tomato.notice.dto.req.NoticeCreateReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 远程 Notice 服务
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@FeignClient(contextId = "remoteAccountService", value = ServiceNameConstants.SERVICE_NAME_NOTICE,
        fallbackFactory = RemoteNoticeServiceFallback.class,dismiss404 = true)
public interface RemoteNoticeService {
    @PostMapping("/notice/create")
    Resp<Void> createNotice(@Validated @RequestBody NoticeCreateReq noticeCreateReq);
}
