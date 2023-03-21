use `tomato-goods`;
-- ----------------------------
-- 商品基本信息
-- ----------------------------
drop table if exists `t_goods_info`;
create table `t_goods_info` (
    `goods_name` VARCHAR(255) NOT NULL comment '商品名称',

    `id`                    bigint(20)  unsigned not null auto_increment,
    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='商品基本信息';

insert into `t_goods_info` (`goods_name`) values ('测试商品1');
insert into `t_goods_info` (`goods_name`) values ('测试商品2');
insert into `t_goods_info` (`goods_name`) values ('测试商品3');
insert into `t_goods_info` (`goods_name`) values ('测试商品4');
insert into `t_goods_info` (`goods_name`) values ('测试商品5');