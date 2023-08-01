package com.tomato.order.domain.constants;

import com.tomato.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态
 *
 * @author lizhifu
 * @since  2022/12/2
 */
@AllArgsConstructor
@Getter
public enum OrderStatusEnum implements BaseEnum<String> {
    /**
     * 初始化
     */
    INIT("INIT", "初始化"),
    /**
     * 处理中
     */
    DEAL("DEAL", "处理中"),
    /**
     * 成功
     */
    SUCCESS("SUCCESS", "成功"),
    /**
     * 失败
     */
    FAIL("FAIL", "失败"),
    /**
     * 取消
     */
    CANCEL("CANCEL", "取消"),
    /**
     * 超时
     */
    TIMEOUT("TIMEOUT", "超时"),
    ;
    private final String value;

    private final String desc;
}
