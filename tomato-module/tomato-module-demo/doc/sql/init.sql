-- ----------------------------
-- demo数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato-demo` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
use `tomato-demo`;
-- ----------------------------
-- 用户信息表
-- ----------------------------
drop table if exists `t_demo_user`;
create table `t_demo_user`  (
    `login_name`    varchar(30)  not null comment '登录帐号',
    `login_pwd`     varchar(128)  not null comment '登录密码',

    `id`            bigint(20) unsigned not null auto_increment comment '主键',
    `version`       int default 0 not null comment '乐观锁',
    `update_time`   datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`   datetime not null default current_timestamp comment '创建时间',
    primary key (`id`)
)engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='用户信息';

-- ----------------------------
-- 用户日志表
-- ----------------------------
drop table if exists `t_demo_user_log`;
create table `t_demo_user_log`  (
    `user_id` bigint NOT NULL COMMENT '用户编号',
    `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',

    `id`            bigint(20) unsigned not null auto_increment comment '主键',
    `version`       int default 0 not null comment '乐观锁',
    `update_time`   datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`   datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    key `idx_user_id` (`user_id`)
)engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_bin comment='用户日志表';