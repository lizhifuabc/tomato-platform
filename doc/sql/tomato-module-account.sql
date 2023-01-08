# 账户信息
drop table if exists `t_account_info`;
create table `t_account_info` (
  `id`                      bigint(20)      not null auto_increment             comment '自增主键',
  `account_no`              varchar(36)     not null                            comment '账户编号',
  `account_his_serial`      bigint(20)      not null default 0                  comment '账户历史流水顺序号',
  `account_manage_serial`   bigint(20)      not null default 0                  comment  '账户管理顺序号',
  `account_type`            varchar(36)     not null                            comment '账户类型',
  `merchant_no`             varchar(50)     not null                            comment '商户编号',
  `balance`                 decimal(16,2)   not null default 0                  comment '余额',
  `out_reserve_balance`     decimal(16,2)   not null default 0                  comment '风险预存期外余额',
  `yesterday_balance`       decimal(16,2)   not null default 0                  comment '上日账户余额',
  `frozen_balance`          decimal(16,2)   not null default 0                  comment '冻结金额',
  `last_trad_time`          datetime                                            comment '上一次交易日期',
  `account_status`          varchar(36)     not null                            comment '账户状态',

  `version`                 int             not null default 0                  comment '乐观锁',
  `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
  `create_time`             datetime        not null default current_timestamp comment '创建时间',
  primary key (`id`),
  unique key `uniq_merchant_no_account_type` (`merchant_no`,`account_type`),
  unique key `uniq_account_no` (`account_no`)
) engine=innodb auto_increment=1 default charset=utf8 comment '账户信息';

# 异步入账账户
drop table if exists `t_account_async`;
create table `t_account_async` (
    `id`                      bigint(20)      not null auto_increment             comment '自增主键',
    `account_no`              varchar(36)     not null                            comment '账户编号',
    `merchant_no`             varchar(50)     not null                            comment '商户编号',

    `version`                 int             not null default 0                  comment '乐观锁',
    `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`             datetime        not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key `uniq_account_no` (`account_no`)
) engine=innodb auto_increment=1 default charset=utf8 comment '异步入账账户';

-- 账户管理历史表
drop table if exists `t_account_manage_his`;
create table `t_account_manage_his`
(
    `id`                      bigint(20)      not null auto_increment             comment '自增主键',
    `account_no`              varchar(36)     not null                            comment '账户编号',
    `account_manage_serial`   bigint(20)      not null                            comment  '账户管理顺序号',
    `before_value`            varchar(500)                                        comment  '改变前值',
    `after_value`             varchar(500)                                        comment  '改变后值',
    `version`                 int             not null default 0                  comment '乐观锁',
    `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`             datetime        not null default current_timestamp comment '创建时间',
    primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8 comment '账户管理历史表';


DROP TABLE IF EXISTS `t_account_his`;
CREATE TABLE `t_account_his` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `account_his_serial`      bigint(20)                comment '账户历史流水顺序号',
   `account_no` varchar(36) NOT NULL COMMENT '账户编号',
   `before_balance` decimal(16,2)   COMMENT '发生前余额',
   `after_balance` decimal(16,2)    COMMENT '发生后余额',
   `amount` decimal(16,2) NOT NULL  COMMENT '发生金额',
   `fee_amount` decimal(16,2) NOT NULL default 0 COMMENT '手续费金额',
   `allow_sett` tinyint(1) not null comment '是否允许结算【0-否, 1-是】',
   `complete_sett` tinyint(1) not null default 0 comment '是否完成结算【0-否, 1-是】',
   `third_no` varchar(36) NOT NULL  COMMENT '第三方流水号',
   `account_his_type` varchar(36) NOT NULL  COMMENT '类型',
   `complete_time` datetime comment '入账完成时间',
   `account_status` varchar(36)  NOT NULL COMMENT '入账状态',

   `version` int default 0 not null comment '乐观锁',
   `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time` datetime not null default current_timestamp comment '创建时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uniq_account_third_no` (account_no,third_no)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '账户历史表';

# 账户结算规则
drop table if exists `t_account_settle`;
create table `t_account_settle` (
   `id`                      bigint(20)      not null auto_increment             comment '自增主键',
   `account_no`              varchar(36)     not null                            comment '账户编号',
   `merchant_no`             varchar(50)     not null                            comment '商户编号',

   `settle_type`             varchar(36)     not null                            comment '结算类型',
   `cycle_type`              varchar(36)     not null                            comment '结算周期',
   `cycle_data`              varchar(36)     not null                            comment '结算周期数据',
   `reserve_days`            int             not null                            comment '风险预存期',
   `min_amount`              decimal(16,2)   not null default 0                  comment '最小结算金额',
   `settle_fee_flag`         smallint        not null default 0                  comment '是否承担划款手续费标志',

   `limit_settle_fee`        decimal(16,2)   not null default 0                  comment '客户不承担手续费限额',
   `settle_fee`              decimal(16,2)   not null default 0                  comment '结算手续费',
   `max_settle_fee`          decimal(16,2)   not null default 0                  comment '封顶手续费',
   `max_settle_days`         int             not null                            comment '最大结算天数',

   `settle_target_type`      varchar(16)     not null                            comment '结算到目标账户类型',
   `remark_code`             varchar(36)     not null                            comment '备注编码',
   `remark_caption`          varchar(36)     not null                            comment '备注',
   `remit_memo`              varchar(400)    not null                            comment '打款备注的表达式，可以动态计算',
   `remit_memo_formula`      varchar(200)    not null                            comment '打款备注',

   `is_urgent`               smallint         not null default 0                 comment '是否加急',
   `is_auto_remit`           smallint         not null default 0                 comment '是否自动打款',

   `version`                 int             not null default 0                  comment '乐观锁',
   `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time`             datetime        not null default current_timestamp comment '创建时间',
   primary key (`id`),
   unique key `uniq_account_no` (`account_no`)
) engine=innodb auto_increment=1 default charset=utf8 comment '账户结算规则';

-- 目标银行卡(结算到银行卡使用)
drop table if exists  t_account_bankcard;
create table  t_account_bankcard(
    `id`                      bigint(20)        not null auto_increment             comment '自增主键',
    `account_no`              varchar(36)       not null                            comment '账户编号',
    `merchant_no`             varchar(50)       not null                            comment '商户编号',
    `bank_code`               varchar(30)       not null                            comment '银行代码',
    `bank_name`               varchar(64)       not null                            comment '银行名称',
    `bank_address`            varchar(128)                                          comment '银行地址',
    `bank_branch`             varchar(128)                                          comment '银行地址',
    `card_no` 				  varchar(33)       not null                            comment '银行卡号',
    `account_name` 			  varchar(64)       not null                            comment '银行开户姓名',
    `card_type` 			  varchar(20)       not null                            comment '银行卡类型(对私/对公)',
    `province` 				  varchar(30)                                           comment '省',
    `city` 					  varchar(30)                                           comment '市',
    `city_name` 			  varchar(30)                                           comment '市名称',
    `settle_flag` 			  bit               not null                            comment '是否默认结算银行卡(扩展字段), 0-否, 1-是',
    `remark`               	  varchar(128)                                          comment '备注',
    `version`                 int               not null default 0                  comment '乐观锁',
    `update_time`             datetime          not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`             datetime          not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key `uniq_account_no` (`account_no`)
)engine=innodb auto_increment=1 default charset=utf8 comment '目标银行卡(结算到银行卡使用)';


DROP TABLE IF EXISTS `t_account_daily_collect`;
create table `t_account_daily_collect`
(
    `account_daily_collect_id`              bigint(20) NOT NULL  COMMENT '主键',
    `account_no`                            varchar(36) NOT NULL COMMENT '账户编号',
    `collect_date`                          date not null comment '汇总日期',
    `total_amount`                          decimal(16,2) not null comment '交易总金额',
    `total_count`                           int not null comment '交易总笔数',
    `sett_status`                           tinyint(1) not null default 1 comment '结算状态【0->已结算；1->未结算】',
    `remark`                                varchar(300) comment '备注',
    `risk_day`                              int  not null comment '风险预存期天数',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`account_daily_collect_id`),
    UNIQUE KEY `uniq_account_collect_date` (account_no,collect_date)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日待结算汇总';