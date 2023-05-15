package com.tomato.notice.dto;

import lombok.Data;

/**
 * 商户通知mq
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@Data
public class NoticeDelayBO {
    /**
     * id
     */
    private Long id;
    /**
     * 通知次数
     */
    private Integer noticeCount;
}
