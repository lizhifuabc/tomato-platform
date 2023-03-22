use `tomato-seckill`;
-- ----------------------------
-- 秒杀活动信息
-- ----------------------------
drop table if exists `t_seckill_activity`;
create table `t_seckill_activity`  (
   `id`                    bigint(20) unsigned not null auto_increment,
   `activity_name`         varchar(64) character set utf8mb4 not null comment '活动名称',
   `activity_desc`         varchar(128) character set utf8mb4 default null comment '活动描述',
   `start_time`            datetime not null comment '开始时间',
   `end_time`              datetime not null comment '结束时间',
   `disabled_flag`         tinyint unsigned default 0 NOT NULL COMMENT '是否被禁用 0否1是',

   `version` int default 0 not null comment '乐观锁',
   `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time` datetime not null default current_timestamp comment '创建时间',
   primary key (`id`),
   index idx_start_time_end_time(`start_time`,`end_time`)
)engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='秒杀活动信息';

insert into `t_seckill_activity` (`activity_name`,`activity_desc`,`start_time`,`end_time`) values ('早8点活动','早8点活动','2022-03-22 08:00:00',DATE_ADD(NOW(), INTERVAL 30 DAY));
insert into `t_seckill_activity` (`activity_name`,`activity_desc`,`start_time`,`end_time`) values ('早10点活动','早10点活动','2022-03-22 10:00:00',DATE_ADD(NOW(), INTERVAL 30 DAY));
insert into `t_seckill_activity` (`activity_name`,`activity_desc`,`start_time`,`end_time`) values ('晚14点活动','晚14点活动','2022-03-22 14:00:00',DATE_ADD(NOW(), INTERVAL 30 DAY));


-- ----------------------------
-- 秒杀活动商品
-- ----------------------------
drop table if exists `t_seckill_goods`;
create table `t_seckill_goods`  (
    `id`                    bigint(20) unsigned not null auto_increment,
    `seckill_activity_id`     bigint(20) not null comment '活动id',
    `goods_id`              bigint(20) not null comment '商品id',

    `seckill_price`           decimal(14,2) not null  comment '秒杀价格',
    `seckill_count`           int not null  comment '秒杀总量',
    `seckill_remaining`       int not null  comment '秒杀剩余量',
    `seckill_limit`           int not null  default 1 comment '每人限购数量',
    `seckill_sort`            int not null  default 0 comment '排序',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key uk_seckill_activity_id_goods_id(`seckill_activity_id`,`goods_id`)
)engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='秒杀活动商品';

insert into `t_seckill_goods` (`id`,`seckill_activity_id`,`goods_id`,`seckill_price`,`seckill_count`,`seckill_remaining`,`seckill_limit`,`seckill_sort`,`version`,`update_time`,`create_time`) values (1,1,1,1.00,100,100,1,0,0,now(),now());
insert into `t_seckill_goods` (`id`,`seckill_activity_id`,`goods_id`,`seckill_price`,`seckill_count`,`seckill_remaining`,`seckill_limit`,`seckill_sort`,`version`,`update_time`,`create_time`) values (2,1,2,2.00,100,100,1,0,0,now(),now());
-- ----------------------------
-- 用户参与活动记录
-- ----------------------------
drop table if exists `t_seckill_user`;
create table `t_seckill_user` (
`id`                    bigint(20)  unsigned not null auto_increment,
`seckill_goods_id`        bigint(20)  not null comment '秒杀活动商品记录id',
`user_id`               bigint(20)  not null comment '用户id',
`goods_id`              bigint(20)  not null comment '商品id（冗余）',

`seckill_count`           int not null  comment '秒杀总量',
`seckill_remaining`       int not null  comment '秒杀剩余量',

`version` int default 0 not null comment '乐观锁',
`update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
`create_time` datetime not null default current_timestamp comment '创建时间',
primary key (`id`),
unique key `unique_activity_relation_id` (`user_id`,`seckill_goods_id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='用户参与活动记录';

-- ----------------------------
-- 用户参与活动明细
-- ----------------------------
drop table if exists `t_seckill_user_detail`;
create table `t_seckill_user_detail` (
     `id`                    bigint(20)  unsigned not null auto_increment,
     `seckill_user_id`       bigint(20)  not null comment '用户id',

     `version` int default 0 not null comment '乐观锁',
     `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
     `create_time` datetime not null default current_timestamp comment '创建时间',
     primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='用户参与活动明细';