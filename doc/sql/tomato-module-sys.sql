-- ----------------------------
-- 用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `login_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录帐号',
    `login_pwd` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
    `actual_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户姓名',
    `gender` tinyint(1) NOT NULL DEFAULT 0 COMMENT '性别',
    `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
    `department_id` int(0) NOT NULL COMMENT '部门id',
    `disabled_flag` tinyint unsigned NOT NULL COMMENT '是否被禁用 0否1是',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_login_name`(`login_name`) USING BTREE,
    UNIQUE INDEX `uk_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

INSERT INTO `t_sys_user` VALUES (1, 'admin', '$2a$10$uuL1feDWb1kmfP2Xeqvj2OhiWh11Gdl3QCZ/vojZTWtjHdpFf4Mce', '管理员', 0, '13500000000', 1, 0, '管理员', 0, '2022-10-22 19:33:02', '2018-05-11 09:38:54');

-- ----------------------------
-- 菜单表
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
    `menu_type` int(0) NOT NULL COMMENT '类型',
    `parent_id` bigint(0) NOT NULL COMMENT '父菜单ID',
    `sort` int(0) NULL DEFAULT NULL COMMENT '显示顺序',
    `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
    `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
    `perms_type` int(0) NULL DEFAULT NULL COMMENT '权限类型',
    `api_perms` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '后端权限字符串',
    `web_perms` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '前端权限字符串',
    `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
    `context_menu_id` bigint(0) NULL DEFAULT NULL COMMENT '功能点关联菜单ID',
    `frame_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为外链',
    `frame_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '外链地址',
    `cache_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否缓存',
    `visible_flag` tinyint(1) NOT NULL DEFAULT 1 COMMENT '显示状态',
    `disabled_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '禁用状态',
    `deleted_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态',
    `create_user_id` bigint(0) NOT NULL COMMENT '创建人',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    `update_user_id` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `version` int default 0 not null comment '乐观锁',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 208 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

INSERT INTO `t_sys_menu` VALUES (50, '系统设置', 1, 0, 200, '/setting', NULL, NULL, NULL, NULL, 'SettingOutlined',
                                 NULL, 0, NULL, 0, 1, 0, 0, 1, '2021-08-13 16:41:33', 1, '2022-09-14 15:46:51',0);
INSERT INTO `t_sys_menu` VALUES (26, '菜单管理', 2, 50, 0, '/menu/list', '/system/menu/menu-list.vue', NULL, NULL, NULL, 'CopyOutlined',
                                 NULL, 0, NULL, 1, 1, 0, 0, 2, '2021-08-09 15:04:35', 1, '2022-10-16 00:04:19',0);

# 字段类型管理
CREATE TABLE `t_gen_field_type`
(
    `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `column_type`  varchar(200) COMMENT '字段类型',
    `attr_type`    varchar(200) COMMENT '属性类型',
    `package_name` varchar(200) COMMENT '属性包名',
    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`),
    unique key (`column_type`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='字段类型管理';

INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('datetime', 'Date', 'java.util.Date');
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('date', 'Date', 'java.util.Date');
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('tinyint', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('smallint', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('mediumint', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('int', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('integer', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('bigint', 'Long', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('float', 'Float', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('double', 'Double', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('decimal', 'BigDecimal', 'java.math.BigDecimal');
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('bit', 'Boolean', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('char', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('varchar', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('tinytext', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('text', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('mediumtext', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('longtext', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('timestamp', 'Date', 'java.util.Date');
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('NUMBER', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('BINARY_INTEGER', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('BINARY_FLOAT', 'Float', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('BINARY_DOUBLE', 'Double', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('VARCHAR2', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('NVARCHAR', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('NVARCHAR2', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('CLOB', 'String', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('int8', 'Long', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('int4', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('int2', 'Integer', NULL);
INSERT INTO `t_gen_field_type` (column_type, attr_type, package_name) VALUES ('numeric', 'BigDecimal', 'java.math.BigDecimal');