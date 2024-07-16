USE `tomato-sys`;
# 单号生成器定义表
DROP TABLE IF EXISTS `t_serial_number`;
CREATE TABLE `t_serial_number`  (
    `serial_number_id` int(0) NOT NULL,
    `business_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务名称',
    `format` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '格式[yyyy]表示年,[mm]标识月,[dd]表示日,[nnn]表示三位数字',
    `rule_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则格式。none没有周期, year 年周期, month月周期, day日周期',
    `init_number` int unsigned NOT NULL COMMENT '初始值',
    `step_random_range` int unsigned NOT NULL COMMENT '步长随机数',
    `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `last_number` bigint(0) NULL DEFAULT NULL COMMENT '上次产生的单号, 默认为空',
    `last_time` datetime(0) NULL DEFAULT NULL COMMENT '上次产生的单号时间',
    `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`serial_number_id`) USING BTREE,
    UNIQUE INDEX `key_name`(`business_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '单号生成器定义表' ROW_FORMAT = Dynamic;

# serial_number记录表
DROP TABLE IF EXISTS `t_serial_number_record`;
CREATE TABLE `t_serial_number_record`  (
    `serial_number_id` int(0) NOT NULL,
    `record_date` date NOT NULL COMMENT '记录日期',
    `last_number` bigint(0) NOT NULL DEFAULT 0 COMMENT '最后更新值',
    `last_time` datetime(0) NOT NULL COMMENT '最后更新时间',
    `count` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新次数',
    `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `uk_generator`(`serial_number_id`, `record_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'serial_number记录表' ROW_FORMAT = Dynamic;