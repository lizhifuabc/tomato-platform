use `tomato-gen`;
drop table if exists `t_gen_field_type`;
CREATE TABLE `t_gen_field_type`
(
    `data_type`     varchar(200) not null COMMENT '字段类型',
    `attr_type`     varchar(200) not null COMMENT '属性类型',
    `package_name`  varchar(200) COMMENT '属性包名',

    `id`            bigint(20) unsigned not null auto_increment comment '主键',
    `version`       int default 0 not null comment '乐观锁',
    `update_time`   datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time`   datetime not null default current_timestamp comment '创建时间',
    primary key (id),
    unique key (data_type)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='字段类型管理';

INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('datetime', 'LocalDateTime', 'import java.time.LocalDateTime;');
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('date', 'LocalDate', 'import java.time.LocalDate;');
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('tinyint', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('smallint', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('mediumint', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('int', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('integer', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('bigint', 'Long', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('float', 'Float', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('double', 'Double', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('decimal', 'BigDecimal', 'import java.math.BigDecimal;');
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('bit', 'Boolean', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('char', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('varchar', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('tinytext', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('text', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('mediumtext', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('longtext', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('timestamp', 'Date', 'import java.util.Date;');
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('NUMBER', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('BINARY_INTEGER', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('BINARY_FLOAT', 'Float', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('BINARY_DOUBLE', 'Double', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('VARCHAR2', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('NVARCHAR', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('NVARCHAR2', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('CLOB', 'String', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('int8', 'Long', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('int4', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('int2', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (data_type, attr_type, package_name) VALUES ('numeric', 'BigDecimal', 'import java.math.BigDecimal;');