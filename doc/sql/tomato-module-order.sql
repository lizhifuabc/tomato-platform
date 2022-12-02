# 订单表
drop table if exists `t_order_info`;
create table`t_order_info` (
    `id` bigint(20) unsigned not null auto_increment,
    `machine_ip` varchar(40) not null  comment '收单服务器ip',

    `order_no` varchar(36) not null  comment '订单号',
    `pay_no`   varchar(36)           comment '支付成功号',

    `request_amount` decimal(14,4)  not null  comment '订单金额',

    `order_status`   varchar(36) not null comment '订单状态',
    `account_status` varchar(36) not null comment '入账状态',
    `refund_status`  varchar(36) not null comment '退款状态',
    `notice_status`  varchar(36) not null comment '通知状态',

    `complete_time` datetime default null comment '完成时间',
    `timeout_time`  datetime not null     comment '超时时间',

    `order_type` tinyint(1) unsigned not null comment '订单类型：1 标准版 、0 专业版',
    `pay_type`   tinyint(1) unsigned not null comment '支付方式：1 微信扫码 、0 支付宝扫码',

    `merchant_order_no` varchar(36)    not null comment '商户订单号',
    `merchant_no`       varchar(16)    not null comment '商户编号',
    `merchant_fee`      decimal(14,4)  not null comment '手续费',
    `merchant_rate`     decimal(14,4)  not null comment '费率快照',
    `merchant_name`     varchar(256)   not null comment '商户名称(冗余字段)',

    `notice_web` varchar(128)  default null comment '页面通知地址',
    `notice_sys` varchar(128)  default null comment '系统通知地址',
    `ext_param`  VARCHAR(128) default null comment '商户扩展参数',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
     primary key (id) USING BTREE,
     unique key uk_order_no (order_no) USING BTREE,
     unique key uk_merchant_no_merchant_order_no (merchant_no,merchant_order_no) USING BTREE,
     unique key uk_pay_no (pay_no) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;