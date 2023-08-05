# 订单表 bigint(20),数据库显示的时候只能显示20位数字
drop table if exists `t_order_info`;
create table`t_order_info` (
    `id` bigint(20) unsigned not null auto_increment,
    `machine_ip` varchar(32) not null  comment '收单服务器ip',
    `client_ip`  varchar(32) not null comment '客户端ip',

    `order_no` varchar(36) not null  comment '订单号',

    `request_amount` decimal(14,4)  not null  comment '订单金额',

    `order_status`   varchar(36) not null comment '订单状态',
    `account_status` varchar(36) comment '入账状态',
    `refund_status`  varchar(36) comment '退款状态',
    `notice_status`  varchar(36) comment '通知状态',

    `complete_time` datetime default null comment '完成时间',
    `timeout_time`  datetime not null     comment '订单失效时间',

    `order_type` tinyint(1) unsigned not null comment '订单类型：1 标准版 、2 专业版',
    `pay_type`   tinyint(1) unsigned not null comment '支付方式：1 微信扫码 、2 支付宝扫码',

    `merchant_order_no` varchar(36)    not null comment '商户订单号',
    `merchant_no`       varchar(64)    not null comment '商户编号',
    `merchant_fee`      decimal(14,4)  not null comment '手续费',
    `merchant_rate`     decimal(14,4)  not null comment '商户手续费费率快照',
    `merchant_name`     varchar(256)   not null comment '商户名称(冗余字段)',

    `notice_web` varchar(128)  comment '页面通知地址',
    `notice_sys` varchar(128)  not null comment '系统通知地址',
    `ext_param`  varchar(128)  comment '商户扩展参数',

    `channel_code`     varchar(128) default null comment '渠道返回码',
    `channel_msg`      varchar(256) default null comment '渠道返回信息',
    `channel_order_no` varchar(64)  default null comment '渠道返回订单号',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
     primary key (`id`) using btree,
     unique key uk_order_no (`order_no`) using btree,
     unique key uk_merchant_no_merchant_order_no (`merchant_no`,`merchant_order_no`) using btree,
     index(`create_time`) using btree,
     index(`timeout_time`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_unicode_ci comment = '订单表' row_format = dynamic;

# 订单表扩展
drop table if exists `t_order_info_extend`;
create table`t_order_info_extend` (
    `id` bigint(20) unsigned not null auto_increment,
    `order_no` varchar(36) not null  comment '订单号',
     primary key (`id`) using btree,
     unique key uk_order_no (`order_no`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单表扩展';

# 订单分表 TODO 看看后续是否放在nacos中
drop table if exists `t_order_sharding_table`;
create table`t_order_sharding_table` (
  `id`              bigint(20)  unsigned not null   auto_increment,
  `start_time`      datetime    not null            comment '开始时间',
  `end_time`        datetime    not null            comment '结束时间',
  primary key (`id`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单分表';
insert into t_order_sharding_table(start_time,end_time) values('2023-08-01 00:00:00','2023-08-31 23:59:59');
insert into t_order_sharding_table(start_time,end_time) values('2023-09-01 00:00:00','2023-09-30 23:59:59');
insert into t_order_sharding_table(start_time,end_time) values('2023-10-01 00:00:00','2023-10-31 23:59:59');
insert into t_order_sharding_table(start_time,end_time) values('2023-11-01 00:00:00','2023-11-30 23:59:59');
insert into t_order_sharding_table(start_time,end_time) values('2023-12-01 00:00:00','2023-12-31 23:59:59');

# 订单分库 TODO 看看后续是否放在nacos中 TODO 可以增加时间段控制，这样可以控制订单的分库
drop table if exists `t_order_sharding_db`;
create table`t_order_sharding_db` (
    `id`                    bigint(20)  unsigned not null   auto_increment,
    `merchant_no_spilt`     varchar(64)    not null comment '商户编号后六位',
    `sharding_db`           varchar(64)    not null comment '指定数据库名',
    primary key (`id`) using btree,
    unique key uk_merchant_no_spilt (`merchant_no_spilt`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单分库';

insert into tomato_order_0.t_order_sharding_db(t_order_sharding_db.merchant_no_spilt,t_order_sharding_db.sharding_db) values('001001','ds_1');
insert into tomato_order_1.t_order_sharding_db(t_order_sharding_db.merchant_no_spilt,t_order_sharding_db.sharding_db) values('001001','ds_1');