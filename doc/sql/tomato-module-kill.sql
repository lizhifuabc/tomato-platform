use `tomato-kill`;
-- ----------------------------
-- 秒杀活动
-- ----------------------------
drop table if exists `t_kill_activity`;
create table `t_kill_activity`  (
    `id`                    bigint(20) unsigned not null auto_increment,
    `start_time`            datetime not null comment '开始时间',
    `end_time`              datetime not null comment '结束时间',
    `disabled_flag`         tinyint unsigned default 0 NOT NULL COMMENT '是否被禁用 0否1是',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    index idx_start_time_end_time(`start_time`,`end_time`)
)engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='秒杀活动';


-- ----------------------------
-- 秒杀活动商品信息
-- ----------------------------
drop table if exists `t_kill_goods`;
create table `t_kill_goods`  (
    `id`                    bigint(20) unsigned not null auto_increment,
    `kill_activity_id`      bigint(20) not null comment '活动id',
    `goods_id`              bigint(20) not null comment '商品id',

    `kill_price`           decimal(14,2) not null  comment '秒杀价格',
    `kill_count`           int not null  comment '秒杀总量',
    `kill_remaining`       int not null  comment '秒杀剩余量',
    `kill_limit`           int not null  default 1 comment '每人限购数量',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key uk_kill_activity_id_goods_id(`kill_activity_id`,`goods_id`)
)engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='秒杀活动商品信息';

-- ----------------------------
-- 用户参与活动记录
-- ----------------------------
drop table if exists `t_kill_user`;
create table `t_kill_user` (
    `id`                    bigint(20)  unsigned not null auto_increment,
    `kill_goods_id`        bigint(20)  not null comment '秒杀活动商品记录id',
    `user_id`               bigint(20)  not null comment '用户id',
    `goods_id`              bigint(20)  not null comment '商品id（冗余）',

    `kill_count`           int not null  comment '秒杀总量',
    `kill_remaining`       int not null  comment '秒杀剩余量',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key `unique_activity_relation_id` (`user_id`,`kill_goods_id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='用户参与活动记录';

-- ----------------------------
-- 用户参与活动明细
-- ----------------------------
drop table if exists `t_kill_user_detail`;
create table `t_kill_user_detail` (
    `id`                    bigint(20)  unsigned not null auto_increment,
    `kill_user_id`       bigint(20)  not null comment '用户id',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='用户参与活动明细';