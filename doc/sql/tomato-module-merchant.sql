# schema
drop schema if exists `tomato_merchant`;
create schema `tomato_merchant`;
use `tomato_merchant`;
## JPA 自动创建表
-- auto-generated definition
drop table if exists `t_merchant_incr`;
create table `t_merchant_incr`
(
    `id`          bigint auto_increment primary key,
    `merchant_no` bigint        null,
    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间'
);
INSERT INTO t_merchant_incr(`merchant_no`) VALUES (1000);
