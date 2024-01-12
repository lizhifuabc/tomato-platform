use `tomato_notice`;
# 通知记录表
drop table if exists `t_notice_record`;
create table `t_notice_record` (
   `id`                 bigint(20)          not null auto_increment comment '通知记录id',
   `order_no`           varchar(36)         not null                comment '订单号',
   `merchant_no`        varchar(64)         not null                comment '商户编号',
   `merchant_order_no`  varchar(36)         not null                comment '商户订单号',
   `rule_code`          varchar(50)         default 'default'       comment '规则编码',
   `http_method`        varchar(32)                                 comment 'http方法',
   `notice_url`         varchar(255)        not null                comment '通知地址',
   `notice_param`       json                not null                comment '通知参数',
   `notice_result` text default null comment '最后一次通知响应结果',
   `notice_count` int(11) not null default '0' comment '通知次数',
   `notice_count_limit` int(11) not null default '6' comment '最大通知次数, 默认6次',
   `state` tinyint(6) not null default '1' comment '通知状态,1-通知中,2-通知成功,3-通知失败',
   `last_notice_time` datetime not null default current_timestamp comment '最后一次通知时间',
   -- `last_notice_record_history` bigint(20) default null comment '最后一次通知记录历史id',
   `version` int default 0 not null comment '乐观锁',
   `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time` datetime not null default current_timestamp comment '创建时间',
   primary key (`id`),
   unique index index_merchant_no_merchant_order_no (`merchant_no`,`merchant_order_no`) using btree,
   index index_order_no (`order_no`) using btree,
   index index_create_time (`last_notice_time`) using btree
) engine=innodb auto_increment=1 default charset=utf8mb4 comment='通知记录表';

# 通知规则
drop table if exists `t_notice_rule`;
create table `t_notice_rule`(
   `id`                bigint primary key not null auto_increment comment '主键',
   `rule_code`         varchar(50)       not null       comment '规则编码',
   `rule_name`         varchar(50)       not null        comment '规则名称',
   `max_retry_times`   smallint          comment '最大重试次数',
   `retry_delay`       smallint          comment '重试延迟阀值',
   `http_method`       varchar(32)       comment 'http方法',
   `handler`           varchar(200)      comment '响应处理器',
   `handler_char_set`  varchar(50)       comment '处理器字符集',
   `handler_params`    varchar(200)      comment '响应处理器参数',
   `desc`              varchar(200)      comment '规则描述',
   `biz_code`          varchar(50)       comment' 所属业务',
   `response_code`     varchar(64)       comment '响应码',
   `computer_room`     varchar(32)       comment '机房',
   `io`                char(1)           comment '流请求',
   `version` int default 0 not null comment '乐观锁',
   `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time` datetime not null default current_timestamp comment '创建时间',
    unique index index_rule_code (`rule_code`) using btree
)engine=innodb auto_increment=1 default charset=utf8mb4 comment='通知规则';

# 通知记录历史
drop table if exists `t_notice_record_history`;
create table `t_notice_record_history` (
   `id` bigint(20) not null auto_increment comment '通知记录历史id',
   `notice_record_id` bigint(20) not null comment '通知记录id',
   `notice_result` text comment '通知响应结果',
   `state` tinyint(6) not null default '1' comment '通知状态,1-通知中,2-通知成功,3-通知失败',
   `cost_time` int  comment '通知耗时',
   `create_time` datetime not null default current_timestamp comment '通知时间',
   `complete_time` datetime default null comment '通知完成时间',
   primary key (`id`),
   index index_notice_record_id (`notice_record_id`) using btree
) engine=innodb auto_increment=1 default charset=utf8mb4 comment='通知记录历史';