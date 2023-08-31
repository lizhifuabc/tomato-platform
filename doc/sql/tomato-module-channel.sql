# 支付渠道 TODO 需要引入规则引擎
CREATE TABLE t_pay_channel (
    id                          bigint          AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    channel_no                  VARCHAR(36)     NOT NULL                COMMENT '渠道编号',
    channel_name                VARCHAR(100)    NOT NULL                COMMENT '渠道名称',
    channel_rate                DECIMAL(10, 2)  NOT NULL                COMMENT '渠道成本百分比，例如0.02表示2%',
    channel_description         VARCHAR(255)    default null            COMMENT '渠道描述',
    status                      ENUM('ACTIVE', 'INACTIVE') NOT NULL     COMMENT '渠道状态，active表示可用，inactive表示不可用',
    pay_type                    VARCHAR(50)     NOT NULL                COMMENT '支付方式',
    transaction_limit           DECIMAL(15, 2)  default null            COMMENT '渠道交易限额',
    processing_time             TIME            default null            COMMENT '渠道平均交易处理时间',
    daily_limit                 DECIMAL(15, 2)  default null            COMMENT '渠道单日限额',
    daily_transactions_limit    INT             default null            COMMENT '渠道单日笔数限额',
    single_transaction_limit    DECIMAL(15, 2)  default null            COMMENT '渠道单笔金额限制',
    daily_accumulated_limit     DECIMAL(15, 2)  default null            COMMENT '渠道单日资金累计限额',
    daily_usage_time            TIME            default null            COMMENT '渠道单日可使用时间'
)engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_unicode_ci  COMMENT '渠道表，用于存储不同支付渠道的信息' row_format = dynamic;

# 支付路由规则表
CREATE TABLE t_router_rule (
    id                  bigint AUTO_INCREMENT PRIMARY KEY       COMMENT 'ID',
    rule_no             VARCHAR(50)     NOT NULL                COMMENT '路由规则编号',
    rule_name           VARCHAR(100)    NOT NULL                COMMENT '路由规则名称',
    pay_type            VARCHAR(50)     NOT NULL                COMMENT '支付方式',
    rule_description    VARCHAR(255)    default null            COMMENT '路由规则描述',
    status              ENUM('ACTIVE', 'INACTIVE')              NOT NULL COMMENT '路由规则状态，active表示启用，inactive表示禁用',
    filter_type         ENUM('COST', 'RESPONSE_TIME', 'WEIGHT') NOT NULL COMMENT '筛选方式，可以是成本、响应时间或权重'
) COMMENT '支付路由规则表，用于存储不同支付路由规则的信息';

# 路由与渠道绑定表
CREATE TABLE t_router_channel_binding (
    id              bigint          AUTO_INCREMENT PRIMARY KEY  COMMENT 'ID',
    rule_no         VARCHAR(50)     NOT NULL                    COMMENT '路由规则编号',
    channel_no      VARCHAR(36)     NOT NULL                    COMMENT '渠道编号',
    weight          INT             NOT NULL                    COMMENT '绑定权重，用于选择优先级',
    unique key uk_rule_channel (`rule_no`, `channel_no`)
) COMMENT '路由与渠道绑定表，用于存储路由规则与渠道之间的关联关系';

# 商户绑定路由规则表
CREATE TABLE t_merchant_router_rule (
    id              bigint          AUTO_INCREMENT PRIMARY KEY  COMMENT 'ID',
    merchant_no     varchar(64)     NOT NULL                    COMMENT '商户编号',
    rule_no         VARCHAR(50)     NOT NULL                    COMMENT '路由规则编号',
    binding_type    ENUM('DEFAULT', 'BACKUP') NOT NULL COMMENT '绑定类型，默认或备份',
    unique key uk_merchant_rule (`merchant_no`, `rule_no`)
) COMMENT '商户绑定路由规则表，用于存储商户和路由规则之间的关联关系';


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