package com.tomato.notice.api.fallback;

import com.tomato.common.resp.Resp;
import com.tomato.notice.api.RemoteNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Fallback 处理类
 * FallbackFactory ： 导致回退触发的原因
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@Slf4j
@Component
public class RemoteNoticeServiceFallback implements FallbackFactory<RemoteNoticeService> {
    @Override
    public RemoteNoticeService create(Throwable cause) {
        return noticeCreateReq -> {
            log.error("远程RemoteNoticeService服务调用失败", cause);
            return Resp.buildFailure(cause.getMessage());
        };
    }
}
