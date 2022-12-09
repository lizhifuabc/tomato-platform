use tomato;
-- ----------------------------
-- 秒杀活动记录
-- ----------------------------
drop table if exists `t_skill_activity`;
create table `t_skill_activity`  (
    `id`                    bigint(20) unsigned not null auto_increment,
    `activity_name`         varchar(64) character set utf8mb4 not null comment '活动名称',
    `activity_desc`         varchar(128) character set utf8mb4 default null comment '活动描述',
    `start_time`            datetime not null comment '开始时间',
    `end_time`              datetime  comment '结束时间，为空永不过期',
    `activity_status`       bit default 0 not null comment '是否关闭,0-否, 1-是',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    index idx_start_time(`start_time`)
)engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='秒杀活动记录';


-- ----------------------------
-- 秒杀活动商品记录
-- ----------------------------
drop table if exists `t_skill_goods`;
create table `t_skill_goods`  (
    `id`                    bigint(20) unsigned not null auto_increment,
    `skill_activity_id`     bigint(20) not null comment '活动id',
    `goods_id`              bigint(20) not null comment '商品id',

    `skill_price`           decimal(14,2) not null  comment '秒杀价格',
    `skill_count`           int not null  comment '秒杀总量',
    `skill_surplus_count`   int not null comment '秒杀剩余量',
    `skill_limit`           int not null  comment '每人限购数量',
    `skill_sort`            int not null default 0 comment '排序',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    index inx_skill_activity_id(`skill_activity_id`)
)engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='秒杀活动商品记录';

-- ----------------------------
-- 用户参与活动记录
-- ----------------------------
drop table if exists `t_skill_user`;
create table `t_skill_user` (
    `id`                    bigint(20)  unsigned not null auto_increment,
    `skill_goods_id`        bigint(20)  not null comment '秒杀活动商品记录id',
    `user_id`               bigint(20)  not null comment '用户id',
    `activity_count`        int(11)     not null default 1 comment '秒杀次数',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key `unique_activity_relation_id` (`user_id`,`activity_count`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='用户参与活动记录';