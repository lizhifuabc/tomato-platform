drop schema if exists `tomato_notice`;
create schema `tomato_notice`;
use `tomato_notice`;
# 通知记录表
DROP TABLE IF EXISTS `t_notice_record`;
CREATE TABLE `t_notice_record` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '通知记录ID',
   `order_no` varchar(36) not null  comment '订单号',
   `merchant_no`       varchar(16)     not null comment '商户编号',
   `merchant_order_no` varchar(36)     not null comment '商户订单号',
   `app_no` VARCHAR(64) NOT NULL COMMENT '系统编号(对接多个系统使用)',
   `notice_url` varchar(256) NOT NULL COMMENT '通知地址',
   `notice_param` json NOT NULL COMMENT '通知参数',
   `notice_result` TEXT DEFAULT NULL COMMENT '最后一次通知响应结果',
   `notice_count` INT(11) NOT NULL DEFAULT '1' COMMENT '通知次数',
   `notice_count_limit` INT(11) NOT NULL DEFAULT '6' COMMENT '最大通知次数, 默认6次',
   `state` TINYINT(6) NOT NULL DEFAULT '1' COMMENT '通知状态,1-通知中,2-通知成功,3-通知失败',
   `last_notice_time` DATETIME not null default current_timestamp COMMENT '最后一次通知时间',
   `version` int default 0 not null comment '乐观锁',
   `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time` datetime not null default current_timestamp comment '创建时间',
   PRIMARY KEY (`id`),
   index index_merchant_no_merchant_order_no (`merchant_no`,`merchant_order_no`) using btree,
   index index_order_no (`order_no`) using btree,
   index index_create_time (`last_notice_time`) using btree
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4 COMMENT='通知记录表';