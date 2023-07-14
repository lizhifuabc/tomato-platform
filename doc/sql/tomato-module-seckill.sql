use `tomato_seckill`;
-- ----------------------------
-- 秒杀活动信息
-- ----------------------------
drop table if exists `t_seckill_activity`;
create table `t_seckill_activity`  (
   `activity_name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '活动名称',
   `activity_desc`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '活动描述',
   `start_time`            datetime(0) not null comment '开始时间',
   `end_time`              datetime(0) not null comment '结束时间',
   `status`                int(2) NULL DEFAULT 0 COMMENT '状态：0：已发布； 1：上线； 2：下线',

   `id`                    bigint(20) unsigned not null auto_increment comment '主键',
   `version` int default 0 not null comment '乐观锁',
   `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time` datetime not null default current_timestamp comment '创建时间',
   PRIMARY KEY (`id`) USING BTREE,
   index idx_start_time_end_time(`start_time`,`end_time`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '秒杀活动信息';

insert into `t_seckill_activity` (`activity_name`,`activity_desc`,`start_time`,`end_time`) values ('早8点活动','早8点活动','2022-03-22 08:00:00',DATE_ADD(NOW(), INTERVAL 30 DAY));
insert into `t_seckill_activity` (`activity_name`,`activity_desc`,`start_time`,`end_time`) values ('早10点活动','早10点活动','2022-03-22 10:00:00',DATE_ADD(NOW(), INTERVAL 30 DAY));
insert into `t_seckill_activity` (`activity_name`,`activity_desc`,`start_time`,`end_time`) values ('晚14点活动','晚14点活动','2022-03-22 14:00:00',DATE_ADD(NOW(), INTERVAL 30 DAY));


-- ----------------------------
-- 秒杀活动商品
-- ----------------------------
drop table if exists `t_seckill_goods`;
create table `t_seckill_goods`  (
    `id`                    bigint(20) unsigned not null auto_increment,
    `goods_name`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商品名称',
    `seckill_activity_id`   bigint(20) not null comment '活动id',
    `start_time`            datetime(0) not null comment '开始时间',
    `end_time`              datetime(0) not null comment '结束时间',
    `original_price`        decimal(10, 2) NULL DEFAULT 0.00 COMMENT '商品原价格',
    `seckill_price`         decimal(10, 2) NULL DEFAULT 0.00 COMMENT '商品秒杀价格',
    `status`                int(2) NULL DEFAULT 0 COMMENT '状态，0：已发布； 1：上线； 2：下线',


    `seckill_count`         int not null default 0 comment '秒杀总量',
    `seckill_remaining`     int not null default 0 comment '秒杀剩余量',
    `seckill_limit`         int not null  default 1 comment '每人限购数量',
    `seckill_sort`          int not null  default 0 comment '排序',

    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商品描述',
    `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商品图片',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '秒杀活动商品';

-- ----------------------------
-- 秒杀订单表
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order`  (
    `id`                bigint(20) NOT NULL COMMENT '订单id',
    `user_id`           bigint(20) NULL DEFAULT 0 COMMENT '用户id',
    `goods_id`          bigint(20) NULL DEFAULT 0 COMMENT '商品id',
    `goods_name`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商品名称',
    `activity_price`    decimal(10, 2) NULL DEFAULT 0.00 COMMENT '秒杀价格',
    `quantity`          int(10) NULL DEFAULT 0 COMMENT '下单商品数量',
    `order_price`       decimal(10, 2) NULL DEFAULT 0.00 COMMENT '订单总金额',
    `activity_id`       bigint(20) NULL DEFAULT 0 COMMENT '活动id',
    `status`            int(2) NULL DEFAULT 0 COMMENT '订单状态 1：已创建 2：已支付 0：已取消； -1：已删除',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '秒杀订单表';