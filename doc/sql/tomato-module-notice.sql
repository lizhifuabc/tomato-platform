use `tomato_notice`;
# 通知记录表
DROP TABLE IF EXISTS `t_notice_record`;
CREATE TABLE `t_notice_record` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '通知记录ID',
   `order_no` varchar(36) not null  comment '订单号',
   `merchant_no`       varchar(16)     not null comment '商户编号',
   `merchant_order_no` varchar(36)     not null comment '商户订单号',
   `rule_code`         varchar(50)       not null       comment '规则编码',
   `http_method`       varchar(32)       comment 'http方法',
   `notice_url` varchar(256) NOT NULL COMMENT '通知地址',
   `notice_param` json NOT NULL COMMENT '通知参数',
   `notice_result` TEXT DEFAULT NULL COMMENT '最后一次通知响应结果',
   `notice_count` INT(11) NOT NULL DEFAULT '0' COMMENT '通知次数',
   `notice_count_limit` INT(11) NOT NULL DEFAULT '6' COMMENT '最大通知次数, 默认6次',
   `state` TINYINT(6) NOT NULL DEFAULT '1' COMMENT '通知状态,1-通知中,2-通知成功,3-通知失败',
   `last_notice_time` DATETIME not null default current_timestamp COMMENT '最后一次通知时间',
   -- `last_notice_record_history` BIGINT(20) default NULL COMMENT '最后一次通知记录历史ID',
   `version` int default 0 not null comment '乐观锁',
   `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time` datetime not null default current_timestamp comment '创建时间',
   PRIMARY KEY (`id`),
   unique index index_merchant_no_merchant_order_no (`merchant_no`,`merchant_order_no`) using btree,
   index index_order_no (`order_no`) using btree,
   index index_create_time (`last_notice_time`) using btree
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='通知记录表';

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
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='通知规则';

# 通知记录历史
DROP TABLE IF EXISTS `t_notice_record_history`;
CREATE TABLE `t_notice_record_history` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '通知记录历史ID',
   `notice_record_id` BIGINT(20) NOT NULL COMMENT '通知记录ID',
   `notice_result` text comment '通知响应结果',
   `state` TINYINT(6) NOT NULL DEFAULT '1' COMMENT '通知状态,1-通知中,2-通知成功,3-通知失败',
   `cost_time` int  comment '通知耗时',
   `create_time` datetime not null default current_timestamp comment '通知时间',
   `complete_time` datetime default null comment '通知完成时间',
   PRIMARY KEY (`id`),
   index index_notice_record_id (`notice_record_id`) using btree
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='通知记录历史';