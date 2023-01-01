# TODO 唯一性索引问题，一商户对应多账户 1:1,1:2
DROP TABLE IF EXISTS `t_account_info`;
CREATE TABLE `t_account_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `account_no` varchar(36) NOT NULL COMMENT '账户编号',
  `account_type` varchar(36) NOT NULL COMMENT '账户类型',
  `merchant_no`  varchar(50) not null comment '商户编号',
  `balance` decimal(16,2) NOT NULL DEFAULT '0' COMMENT '余额',
  `unbalance` decimal(16,2) NOT NULL DEFAULT '0' COMMENT '不可用余额',
  `status` tinyint(1) null default 0 comment '状态【0->正常；1->关闭】',
  `risk_day`  int  not null comment '风险预存期天数',

  `version` int default 0 not null comment '乐观锁',
  `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
  `create_time` datetime not null default current_timestamp comment '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_merchant_no_account_type` (`merchant_no`,`account_type`),
  UNIQUE KEY `uniq_account_no` (`account_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '账户表';

DROP TABLE IF EXISTS `t_account_his`;
CREATE TABLE `t_account_his` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `account_his_id` bigint(20) NOT NULL COMMENT '账户历史ID',
   `account_no` varchar(36) NOT NULL COMMENT '账户编号',
   `before_balance` decimal(16,2)   COMMENT '发生前余额',
   `after_balance` decimal(16,2)    COMMENT '发生后余额',
   `amount` decimal(16,2) NOT NULL  COMMENT '发生金额',
   `allow_sett` tinyint(1) not null comment '是否允许结算【0-否, 1-是】',
   `complete_sett` tinyint(1) not null default 0 comment '是否完成结算【0-否, 1-是】',
   `third_no` varchar(36) NOT NULL  COMMENT '第三方流水号',
   `account_his_type` varchar(36) NOT NULL  COMMENT '类型',
   `complete_time` datetime comment '入账完成时间',
   `account_status` tinyint  NOT NULL COMMENT '入账状态',

   `version` int default 0 not null comment '乐观锁',
   `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time` datetime not null default current_timestamp comment '创建时间',
   PRIMARY KEY (`id`),
   unique key `uniq_account_his_id`(`account_his_id`),
   UNIQUE KEY `uniq_account_third_no` (account_no,third_no)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '账户历史表';

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