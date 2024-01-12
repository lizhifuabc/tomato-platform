package com.tomato.notice.api;

import com.tomato.common.domain.resp.Resp;
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
@FeignClient(contextId = "remoteNoticeService", value = ServiceNameConstants.SERVICE_NAME_NOTICE,
		fallbackFactory = RemoteNoticeServiceFallback.class, dismiss404 = true)
public interface RemoteNoticeService {

	/**
	 * 创建通知
	 * @param noticeCreateReq 通知请求
	 * @return Resp 通知结果
	 */
	@PostMapping("/notice/create")
	Resp<Void> createNotice(@Validated @RequestBody NoticeCreateReq noticeCreateReq);

}
