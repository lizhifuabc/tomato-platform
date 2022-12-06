package com.tomato.dynamic.thread.notify.enums;

/**
 * 通知类型
 *
 * @author lizhifu
 * @date 2022/12/6
 */
public enum NotifyTypeEnum {

    /**
     * Config change notify.
     */
    CHANGE("change"),

    /**
     * ThreadPool livenes notify.
     * livenes = activeCount / maximumPoolSize
     */
    LIVENESS("liveness"),

    /**
     * Capacity threshold notify
     */
    CAPACITY("capacity"),

    /**
     * Reject notify.
     */
    REJECT("reject"),

    /**
     * Task run timeout alarm.
     */
    RUN_TIMEOUT("run_timeout"),

    /**
     * Task queue wait timeout alarm.
     */
    QUEUE_TIMEOUT("QUEUE_TIMEOUT");

    private final String value;

    NotifyTypeEnum(String value) {
        this.value = value;
    }

    public static NotifyTypeEnum of(String value) {
        for (NotifyTypeEnum notifyType : NotifyTypeEnum.values()) {
            if (notifyType.value.equals(value)) {
                return notifyType;
            }
        }
        return null;
    }
}
