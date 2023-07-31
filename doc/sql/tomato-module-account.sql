# 账户信息
drop table if exists `t_account_info`;
create table `t_account_info` (
  `id`                      bigint(20)      not null auto_increment                 comment '自增主键',
  `account_no`              varchar(36)     not null                                comment '账户编号',
  `account_his_serial`      bigint(20)      not null default 0                      comment '账户历史流水顺序号',
  `account_manage_serial`   bigint(20)      not null default 0                      comment '账户管理顺序号',
  `account_type`            varchar(36)     not null                                comment '账户类型',
  `merchant_no`             varchar(50)     not null                                comment '商户编号',
  `balance`                 decimal(16,2)   not null default 0                      comment '余额',
  `out_reserve_balance`     decimal(16,2)   not null default 0                      comment '风险预存期外余额',
  `out_reserve_date`        date            not null default (current_date)         comment '风险预存期外余额更新日期',
  `yesterday_balance`       decimal(16,2)   not null default 0                      comment '上日账户余额',
  `frozen_balance`          decimal(16,2)   not null default 0                      comment '冻结金额',
  `last_trad_time`          datetime                                                comment '上一次交易日期',
  `account_status`          varchar(36)     not null default 'ACCOUNT_AVAILABLE'    comment '账户状态',
  `remark`                  varchar(50)                                             comment '备注',

  `version`                 int             not null default 0                      comment '乐观锁',
  `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
  `create_time`             datetime        not null default current_timestamp comment '创建时间',
  primary key (`id`),
  unique key `uniq_merchant_no_account_type` (`merchant_no`,`account_type`),
  unique key `uniq_account_no` (`account_no`)
) engine=innodb auto_increment=1 default charset=utf8 comment '账户信息';

# 账户手续费配置
drop table if exists `t_account_rate`;
create table `t_account_rate` (
    `id`                      bigint(20)      not null auto_increment             comment '自增主键',
    `account_no`              varchar(36)     not null                            comment '账户编号',
    `merchant_no`             varchar(50)     not null                            comment '商户编号',
    `rate`                    decimal(16,2)   not null                            comment '费率',
    `rate_type`               varchar(36)     not null                            comment '费率类型',

    `version`                 int             not null default 0                  comment '乐观锁',
    `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`             datetime        not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key `uniq_account_no` (`account_no`,`rate_type`)
) engine=innodb auto_increment=1 default charset=utf8 comment '账户手续费配置';


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

# 账户历史
DROP TABLE IF EXISTS `t_account_his`;
CREATE TABLE `t_account_his` (
    `id`                         bigint(20)      not null auto_increment             comment '自增主键',
    `account_his_serial`         bigint(20)                                          comment '账户历史流水顺序号',
    `account_no`                 varchar(36)     not null                            comment '账户编号',
    `merchant_no`                varchar(50)     not null                            comment '商户编号',
    `merchant_order_no`          varchar(36)     not null                            comment '商户订单号(同一个商户唯一)',
    `sys_no`                     varchar(50)     not null                            comment '系统流水号（唯一）',
    `before_balance`             decimal(16,2)   default null                        comment '发生前余额',
    `after_balance`              decimal(16,2)   default null                        comment '发生后余额',
    `amount`                     decimal(16,2)   not null                            comment '发生金额',
    `amount_free`                decimal(16,2)   not null                            comment '手续费',
    `amount_rate`                decimal(16,2)   not null                            comment '费率快照',
    `account_his_type`           varchar(36)     not null                            comment '账户历史类型',
    `complete_time`              datetime                                            comment '完成时间',
    `account_his_status`         varchar(36)     not null                            comment '账户历史状态',
    `remark`                     varchar(256)    default null                        comment '备注',

    `version`                   int default 0 not null comment '乐观锁',
    `update_time`               datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`               datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    index (`create_time`),
    index (`account_no`),
    unique key `uniq_account_merchant` (`merchant_no`,`merchant_order_no`),
    unique key `uniq_account_third_no` (`sys_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '账户历史';

# 账户结算规则
drop table if exists `t_account_settle`;
create table `t_account_settle` (
   `id`                      bigint(20)      not null auto_increment             comment '自增主键',
   `account_no`              varchar(36)     not null                            comment '账户编号',
   `merchant_no`             varchar(50)     not null                            comment '商户编号',

   `settle_type`             varchar(36)     not null                            comment '结算类型',
   `cycle_type`              varchar(36)     default 'WEEK_WORK'                 comment '结算周期，默认:周结：每周几结算,工作日，顺延一天，默认周末休息',
   `cycle_data`              varchar(36)     default '1,2,3,4,5'                 comment '结算周期数据(自动结算必须),默认:1,2,3,4,5',
   `reserve_days`            int             default 1                           comment '风险预存期',

   `max_settle_days`         int             not null                            comment '最大结算天数',

   `settle_target_type`      varchar(16)     not null                            comment '结算到目标账户类型',
   `remark`                  varchar(256)                                        comment '备注',

   `version`                 int             not null default 0                  comment '乐观锁',
   `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time`             datetime        not null default current_timestamp comment '创建时间',
   primary key (`id`),
   unique key `uniq_account_no` (`account_no`)
) engine=innodb auto_increment=1 default charset=utf8 comment '账户结算规则';

# 节假日控制
drop table if exists `t_account_work`;
create table `t_account_work` (
    `id`                      bigint(20)      not null auto_increment             comment '自增主键',
    `work_day`                date            not null                            comment '日期',
    `day_type`                tinyint         not null                            comment '0:工作日；1：调休工作日；2：周末休息日；3：法定休息日；',
    `version`                 int             not null default 0                  comment '乐观锁',
    `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`             datetime        not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key (`work_day`)
) engine=innodb auto_increment=1 default charset=utf8 comment '节假日控制';

insert into `t_account_work`(`work_day`,`day_type`) values ('2023-01-14',1);
insert into `t_account_work`(`work_day`,`day_type`) values ('2023-01-15',1);
insert into `t_account_work`(`work_day`,`day_type`) values ('2023-01-16',3);

# 账户结算控制
drop table if exists `t_account_settle_control`;
create table `t_account_settle_control` (
    `id`                      bigint(20)      not null auto_increment             comment '自增主键',
    `account_settle_id`       bigint(20)      not null                            comment '账户结算规则ID',
    `account_no`              varchar(36)     not null                            comment '账户编号',
    `merchant_no`             varchar(50)     not null                            comment '商户编号',

    `settle_record_id`        bigint(20)                                          comment '账户结算记录ID',

    `next_settle_date`        date            not null                            comment '下次结算日期',

    `version`                 int             not null default 0                  comment '乐观锁',
    `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`             datetime        not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key `uniq_account_no` (`account_no`)
) engine=innodb auto_increment=1 default charset=utf8 comment '账户结算控制';

# 账户结算记录
drop table if exists `t_account_settle_record`;
create table `t_account_settle_record` (
    `id`                      bigint(20)      not null auto_increment             comment '自增主键',
    `account_settle_id`       bigint(20)      not null                            comment '账户结算规则ID',
    `account_no`              varchar(36)     not null                            comment '账户编号',
    `merchant_no`             varchar(50)     not null                            comment '商户编号',
    `settle_status`           varchar(36)     not null                            comment '结算状态',
    `settle_remark`           varchar(50)     not null  default '成功'             comment '结算备注',
    `settle_date`             date            not null                            comment '结算日期',

    `settle_amount`           decimal(16,2)   not null                            comment '结算金额',
    `settle_rate`             decimal(16,2)   not null                            comment '结算手续费率',
    `settle_fee`              decimal(16,2)   not null                            comment '结算手续费',

    `version`                 int             not null default 0                  comment '乐观锁',
    `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`             datetime        not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key `uniq_account_settle` (`account_no`,`settle_date`)
) engine=innodb auto_increment=1 default charset=utf8 comment '账户结算记录';

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

# 每日待结算汇总
DROP TABLE IF EXISTS `t_account_daily_collect`;
create table `t_account_daily_collect`
(
    `id`                      bigint(20)        not null auto_increment             comment '自增主键',
    `account_no`              varchar(36)       not null                            comment '账户编号',
    `merchant_no`             varchar(50)       not null                            comment '商户编号',

    `collect_date`            date              not null                            comment '汇总日期',
    `total_amount`            decimal(16,2)     not null                            comment '交易总金额',
    `total_count`             bigint(20)        not null                            comment '交易总笔数',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_account_collect_date` (`account_no`,`collect_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '每日待结算汇总';