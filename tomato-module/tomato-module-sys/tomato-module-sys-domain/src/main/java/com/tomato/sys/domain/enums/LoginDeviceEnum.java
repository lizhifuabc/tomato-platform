package com.tomato.sys.domain.enums;


import com.tomato.common.enums.BaseEnum;

/**
 * 登录设备
 *
 * @author lizhifu
 */
public enum LoginDeviceEnum implements BaseEnum<Integer> {
    /**
     * 电脑端
     */
    PC(1, "电脑端"),
    /**
     * 安卓
     */
    ANDROID(2, "安卓"),
    /**
     * 苹果
     */
    APPLE(3, "苹果"),
    /**
     * H5
     */
    H5(4, "H5"),
    /**
     * 微信小程序
     */
    WX_MP(5, "微信小程序");

    LoginDeviceEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private Integer value;
    private String desc;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
