package com.tomato.cloud.gateway.gray.constant;

import com.tomato.common.enums.BaseEnum;

/**
 * 配置中心元数据常量
 *
 * @author lizhifu
 * @since 2023/7/18
 */
public interface GrayMetadataConstant {
    String VERSION = "version";

    enum VersionStatus implements BaseEnum<String> {
        /**
         * 默认版本号
         */
        DEFAULT("DEFAULT","默认版本号"),
        /**
         * 未知版本号
         */
        UN_KNOW("UN_KNOW","未知版本号"),
        ;
        VersionStatus(String value, String desc) {
            this.desc = desc;
            this.value = value;
        }

        @Override
        public String getDesc() {
            return desc;
        }

        @Override
        public String getValue() {
            return value;
        }
        private final String value;

        private final String desc;
    }
}
