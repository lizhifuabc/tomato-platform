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
    `merchant_no`       varchar(16)    not null comment '商户编号',
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

# 支付方式表
drop table if exists `t_pay_type`;
create table`t_pay_type` (
      `id` bigint(20) unsigned not null auto_increment,
      `pay_type` varchar(36) not null  comment '支付方式',
      `pay_type_desc` varchar(36) not null  comment '支付方式描述',
      primary key (`id`) using btree,
      unique key uk_order_no (`pay_type`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '支付方式表';


# 支付产品表
drop table if exists `t_pay_product`;
create table`t_pay_product` (
     `id` bigint(20) unsigned not null auto_increment,
     `pay_product` varchar(36) not null  comment '支付产品',
     `pay_product_name` varchar(36) not null  comment '支付产品名称',
     `pay_product_desc` varchar(256) default null  comment '支付产品描述',
     primary key (`id`) using btree,
     unique key uk_order_no (`pay_product`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '支付产品表';

insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('1','快捷支付','用户在完成绑卡之后，再支付的时候，不需要再输入卡或者身份信息，仅需要输入支付密码就可以完成支付。对于小额度的支付，甚至可以开通小额免密，直接完成支付。 这种支付方式不会打断用户的体验，是目前主要的在线支付方式。一般快捷支付产品是通过封装银行或者第三方支付平台提供的快捷支付接口或者代付接口来实现的。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('2','网银支付','用户在支付的时候，需要跳转到银行网银页面来完成支付。在网银页面，需要输入用户的卡号和身份信息。这种支付方式会中断用户当前的体验，一般仅用于PC Web上的支付。 网银支付是封装银行提供的网银支付来实现。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('3','协议支付','协议支付也称代收或者代扣，代收指渠道授权商户可以从用户的银行账户中扣款，一般用于定期扣款，不用于日常消费。比如水电煤气、有线电视费。协议支付是通过封装银行、第三方支付提供的代扣或者快捷接口来实现。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('4','平台支付','使用微信、支付宝等第三方支付平台来完成支付。使用时，一般需要用户预先安装支付平台系统（手机上），注册并登录到第三方支付平台，并且已经在该平台上完成绑卡等操作。 由于微信、支付宝已经被大量使用，用户也产生对这些平台的信任，平台支付往往是电商公司的主要支付方式。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('5','外卡支付','对于由海外支付的需求，还需要提供外卡支付支持。 国内不少支付渠道都能支持外卡支付，如支付宝全球购等。直接对接Paypal，也是目前用的最多的外卡支付渠道。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('6','话费支付','对于有包月小额类型的支付，手机话费也是一个不错的选择。目前也有一些平台可以支持话费支付，比如虹软、联动优势等。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('7','虚币支付','不少公司会有自己的虚拟币，比如京豆、Q币等。这些虚币也可以作为一种支付方式。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('8','账户支付','也成为余额支付、零钱支付等。 指为用户建立本地账户， 支持充值，之后可以使用这个账户来完成支付。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('8','信用支付','如京东的白条，蚂蚁花呗等，指使用信用账户进行透支，类似信用卡支付。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('10','代付','和代扣相反，代付是平台将钱打给用户。');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('11','退款','');
insert into t_pay_product(`pay_product`,`pay_product_name`,`pay_product_desc`) values('12','退汇','');