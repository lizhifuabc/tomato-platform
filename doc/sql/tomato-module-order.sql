# t_order_sharding_group
drop table if exists `t_order_sharding_group`;
create table`t_order_sharding_group` (
   `id`         bigint(20)      unsigned not null auto_increment,
   `name`       varchar(36)     not null comment '名称',
   `start_id`   bigint(20)      unsigned not null comment '开始ID',
   `end_id`     bigint(20)      unsigned not null comment '结束ID',
   primary key (`id`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = 'sharding-group表';

drop table if exists `t_order_shard`;
create table`t_order_shard` (
    `id`         bigint(20)      unsigned not null auto_increment,
    `name`       varchar(36)     not null comment '名称',
    `group_id`   bigint(20)      unsigned not null comment 'group_id',
    `hash_value`       varchar(36)     not null comment 'hash值',
    primary key (`id`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = 'sharding-group表';


# 商户维度表：字段少
# 1 解决商户编号+商户订单号查询问题
# 2 防重问题
# 分库分表策略：
drop table if exists `t_order_merchant`;
create table`t_order_merchant` (
  `id`                bigint(20) unsigned not null auto_increment,
  `merchant_no`       varchar(16)     not null comment '商户编号',
  `merchant_order_no` varchar(36)     not null comment '商户订单号',
  `shard_sign`        varchar(16)     not null comment '库号_表号',
  `create_time`       datetime not null default current_timestamp comment '创建时间',
  primary key (`id`) using btree,
  unique key uk_merchant_no_merchant_order_no (`merchant_no`,`merchant_order_no`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '商户维度表';

# 订单表 bigint(20),数据库显示的时候只能显示20位数字
drop table if exists `t_order_info`;
create table`t_order_info` (
    `id` bigint(20) unsigned not null auto_increment,
    `machine_ip` varchar(32) not null  comment '收单服务器ip',
    `client_ip`  varchar(32) default null comment '客户端ip',

    `order_no` varchar(36) not null  comment '订单号',

    `request_amount` decimal(14,4)  not null  comment '订单金额',

    `order_status`   varchar(36) not null comment '订单状态',
    `account_status` varchar(36) not null comment '入账状态',
    `refund_status`  varchar(36) not null comment '退款状态',
    `notice_status`  varchar(36) not null comment '通知状态',

    `complete_time` datetime default null comment '完成时间',
    `timeout_time`  datetime not null     comment '订单失效时间',

    `order_type` tinyint(1) unsigned not null comment '订单类型：1 标准版 、2 专业版',
    `pay_type`   tinyint(1) unsigned not null comment '支付方式：1 微信扫码 、2 支付宝扫码',

    `merchant_order_no` varchar(36)    not null comment '商户订单号',
    `merchant_no`       varchar(16)    not null comment '商户编号',
    `merchant_fee`      decimal(14,4)  not null comment '手续费',
    `merchant_rate`     decimal(14,4)  not null comment '商户手续费费率快照',
    `merchant_name`     varchar(256)   not null comment '商户名称(冗余字段)',

    `notice_web` varchar(128)  default null comment '页面通知地址',
    `notice_sys` varchar(128)  default null comment '系统通知地址',
    `ext_param`  varchar(128)  default null comment '商户扩展参数',

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