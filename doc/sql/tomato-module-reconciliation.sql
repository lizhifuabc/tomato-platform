-- ----------------------------
-- 数据源信息
-- ----------------------------
DROP TABLE IF EXISTS `t_db_info`;
CREATE TABLE `t_db_info` (
    `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `db_name`           varchar(255) NOT NULL COMMENT '名称',
    `driver`            varchar(255) NOT NULL COMMENT 'driver',
    `url`               varchar(255) NOT NULL COMMENT 'url',
    `username`          varchar(255) DEFAULT NULL COMMENT '用户名',
    `password`          varchar(255) DEFAULT NULL COMMENT '密码',
    `version`           int             not null default 0                  comment '乐观锁',
    `update_time`       datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`       datetime        not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='数据源信息';

INSERT INTO t_db_info (db_name, driver, url, username, password)
VALUES ('mydb1', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3306/tomato_reconciliation', 'tomato', 'tomato');

INSERT INTO t_db_info (db_name, driver, url, username, password)
VALUES ('mydb2', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3306/tomato_reconciliation', 'tomato', 'tomato');

-- ----------------------------
-- 对账任务
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
    `id`                    BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `task_name`             varchar(255)    NOT NULL COMMENT '任务名称',
    `task_desc`             varchar(255)    NOT NULL COMMENT '任务描述',
    `task_sign`             varchar(126)    NOT NULL COMMENT '字段标识',

    `up_table_sql`          varchar(255)    not null comment '上游表SQL',
    `up_db_info_id`         BIGINT(20)      not null comment '上游数据源ID',


    `down_table_sql`        varchar(255)    not null comment '下游表SQL',
    `down_db_info_id`       BIGINT(20)      not null comment '下游数据源ID',

    `version`                 int             not null default 0                  comment '乐观锁',
    `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`             datetime        not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='对账任务';

insert into t_task (task_name, task_desc, task_sign, up_table_sql, up_db_info_id, down_table_sql, down_db_info_id)
values ('对账任务1', '对账任务1', 'no', 'select order_no as no,amount from t_demo1', 1, 'select sign_no as no,amount from t_demo2', 2);

-- ----------------------------
-- 对账任务执行结果
-- ----------------------------
DROP TABLE IF EXISTS `t_task_result`;
CREATE TABLE `t_task_result` (
    `id`                      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `task_id`                 BIGINT(20)      not null comment '任务ID',
    `task_sign_value`         varchar(126)    NOT NULL COMMENT '字段标识值',
    `task_value`              text            NOT NULL COMMENT '数据明细值',
    `unilateral_type`         varchar(255)    not null comment '单边类型',
    `task_date`               date            not null comment '任务日期',

    `version`                 int             not null default 0                  comment '乐观锁',
    `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`             datetime        not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='对账任务执行结果';

-- ----------------------------
-- demo1
-- ----------------------------
DROP TABLE IF EXISTS `t_demo1`;
CREATE TABLE `t_demo1` (
     `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
     `order_no`          varchar(255) NOT NULL COMMENT '编号',
     `amount`            decimal(16,4) NOT NULL COMMENT '金额',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='demo1';
insert into t_demo1 (order_no, amount) values ('1000', 100);
insert into t_demo1 (order_no, amount) values ('1001', 200);
insert into t_demo1 (order_no, amount) values ('1002', 300);
-- ----------------------------
-- demo2
-- ----------------------------
DROP TABLE IF EXISTS `t_demo2`;
CREATE TABLE `t_demo2` (
    `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `sign_no`           varchar(255) NOT NULL COMMENT '编号',
    `amount`            decimal(16,4) NOT NULL COMMENT '金额',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='demo2';
insert into t_demo2 (sign_no, amount) values ('1000', 100);
insert into t_demo2 (sign_no, amount) values ('1001', 200.00);
insert into t_demo2 (sign_no, amount) values ('1002', 300.01);
insert into t_demo2 (sign_no, amount) values ('1003', 400.01);