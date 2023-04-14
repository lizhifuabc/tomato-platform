create table t_pay
(
    `id`                bigint(20) unsigned not null auto_increment primary key comment '主键' ,
    `order_no`          varchar(36) not null  comment '订单号',
    `pay_no`            varchar(36) not null comment '支付记录号',
    `pay_status`        varchar(36) not null comment '支付状态',
    `request_amount`    decimal(14,4)  not null  comment '请求金额',
    `product_no`        varchar(36) not null comment '支付产品编号',
    `complete_time`     datetime default null comment '完成时间',
    `timeout_time`      datetime not null     comment '支付失效时间(是否和订单冲突？)',
    `channel_fee`       decimal(14,4)  not null comment '通道手续费',
    `channel_rate`      decimal(14,4)  not null comment '通道费率',

    send_urlmsg varchar(1024) default '' not null comment '通道返回支付信息',
    subchannel_no varchar(36) not null comment '子通道编号',
    subchannel_name varchar(64) not null comment '子通道名称',
    remarks_info varchar(1024) default '' not null comment '支付信息/错误信息',
    ext_content json null comment '支付记录扩展信息',
    channel_flag varchar(64) default '' not null comment '父通道接口标示',
    channel_among varchar(128) default '' not null comment '通道中间传递信息，例如微信openid',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    index ti_pay_complete_time (complete_time),
    index ti_pay_order_no (order_no),
    index ti_pay_create_time (create_time),
    unique index ti_pay_pay_no (pay_no)
) engine=innodb default charset = utf8mb4 comment '支付记录表';