-- ----------------------------
-- 用户信息表
-- ----------------------------
drop table if exists `t_sys_user`;
create table `t_sys_user`  (
    `id` bigint not null auto_increment comment '主键',
    `login_name` varchar(30) character set utf8 collate utf8_general_ci not null comment '登录帐号',
    `login_pwd` varchar(128) character set utf8 collate utf8_general_ci not null comment '登录密码',
    `actual_name` varchar(30) character set utf8 collate utf8_general_ci not null comment '用户姓名',
    `gender` tinyint(1) not null default 0 comment '性别',
    `phone` varchar(15) character set utf8 collate utf8_general_ci null default null comment '手机号码',
    `department_id` int(0) not null comment '部门id',
    `disabled_flag` tinyint unsigned not null comment '是否被禁用 0否1是',
    `remark` varchar(200) character set utf8mb4 collate utf8mb4_unicode_ci null default null comment '备注',

    `version` int default 0 not null comment '乐观锁',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    primary key (`id`) using btree,
    unique index `uk_login_name`(`login_name`) using btree,
    unique index `uk_phone`(`phone`) using btree
) engine = innodb auto_increment = 100 character set = utf8mb4 collate = utf8mb4_unicode_ci comment = '用户信息表' row_format = dynamic;

insert into `t_sys_user` values (1, 'admin', '$2a$10$uul1fedwb1kmfp2xeqvj2ohiwh11gdl3qcz/vojztwtjhdpff4mce', '管理员', 0, '13500000000', 1, 0, '管理员', 0, '2022-10-22 19:33:02', '2018-05-11 09:38:54');

-- ----------------------------
-- 菜单表
-- ----------------------------
drop table if exists `t_sys_menu`;
create table `t_sys_menu`  (
    `id` bigint(0) not null auto_increment comment '菜单id',
    `menu_name` varchar(200) character set utf8mb4 collate utf8mb4_0900_ai_ci not null comment '菜单名称',
    `menu_type` int(0) not null comment '类型',
    `parent_id` bigint(0) not null comment '父菜单id',
    `sort` int(0) null default null comment '显示顺序',
    `path` varchar(255) character set utf8mb4 collate utf8mb4_0900_ai_ci null default null comment '路由地址',
    `component` varchar(255) character set utf8mb4 collate utf8mb4_0900_ai_ci null default null comment '组件路径',
    `perms_type` int(0) null default null comment '权限类型',
    `api_perms` text character set utf8mb4 collate utf8mb4_0900_ai_ci null comment '后端权限字符串',
    `web_perms` text character set utf8mb4 collate utf8mb4_0900_ai_ci null comment '前端权限字符串',
    `icon` varchar(100) character set utf8mb4 collate utf8mb4_0900_ai_ci null default null comment '菜单图标',
    `context_menu_id` bigint(0) null default null comment '功能点关联菜单id',
    `frame_flag` tinyint(1) not null default 0 comment '是否为外链',
    `frame_url` text character set utf8mb4 collate utf8mb4_0900_ai_ci null comment '外链地址',
    `cache_flag` tinyint(1) not null default 0 comment '是否缓存',
    `visible_flag` tinyint(1) not null default 1 comment '显示状态',
    `disabled_flag` tinyint(1) not null default 0 comment '禁用状态',
    `deleted_flag` tinyint(1) not null default 0 comment '删除状态',
    `create_user_id` bigint(0) not null comment '创建人',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    `update_user_id` bigint(0) null default null comment '更新人',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `version` int default 0 not null comment '乐观锁',
    primary key (`id`) using btree
) engine = innodb auto_increment = 208 character set = utf8mb4 collate = utf8mb4_0900_ai_ci comment = '菜单表' row_format = dynamic;

insert into `t_sys_menu` values (50, '系统设置', 1, 0, 200, '/setting', null, null, null, null, 'settingoutlined',
                                 null, 0, null, 0, 1, 0, 0, 1, '2021-08-13 16:41:33', 1, '2022-09-14 15:46:51',0);
insert into `t_sys_menu` values (26, '菜单管理', 2, 50, 0, '/menu/list', '/system/menu/menu-list.vue', null, null, null, 'copyoutlined',
                                 null, 0, null, 1, 1, 0, 0, 2, '2021-08-09 15:04:35', 1, '2022-10-16 00:04:19',0);

-- ----------------------------
-- 操作日志记录
-- ----------------------------
drop table if exists `t_sys_operation_log`;
create table `t_sys_operation_log`  (
   `id` bigint not null auto_increment comment '日志主键',
   `trace_id` varchar(64) character set utf8mb4 collate utf8mb4_unicode_ci not null default '' comment '链路追踪编号',
   `user_id` bigint not null comment '用户编号',
   `user_type` tinyint not null default 0 comment '用户类型',
   `module` varchar(50) character set utf8mb4 collate utf8mb4_unicode_ci not null comment '模块标题',
   `name` varchar(50) character set utf8mb4 collate utf8mb4_unicode_ci not null comment '操作名',
   `type` bigint not null default 0 comment '操作分类',
   `content` varchar(2000) character set utf8mb4 collate utf8mb4_unicode_ci not null default '' comment '操作内容',
   `exts` varchar(512) character set utf8mb4 collate utf8mb4_unicode_ci not null default '' comment '拓展字段',
   `request_method` varchar(16) character set utf8mb4 collate utf8mb4_unicode_ci null default '' comment '请求方法名',
   `request_url` varchar(255) character set utf8mb4 collate utf8mb4_unicode_ci null default '' comment '请求地址',
   `user_ip` varchar(50) character set utf8mb4 collate utf8mb4_unicode_ci null default null comment '用户 ip',
   `user_agent` varchar(200) character set utf8mb4 collate utf8mb4_unicode_ci null default null comment '浏览器 ua',
   `java_method` varchar(512) character set utf8mb4 collate utf8mb4_unicode_ci not null default '' comment 'java 方法名',
   `java_method_args` varchar(8000) character set utf8mb4 collate utf8mb4_unicode_ci null default '' comment 'java 方法的参数',
   `start_time` datetime not null comment '操作时间',
   `duration` int not null comment '执行时长',
   `result_code` int not null default 0 comment '结果码',
   `result_msg` varchar(512) character set utf8mb4 collate utf8mb4_unicode_ci null default '' comment '结果提示',
   `result_data` varchar(4000) character set utf8mb4 collate utf8mb4_unicode_ci null default '' comment '结果数据',
   `creator` varchar(64) character set utf8mb4 collate utf8mb4_unicode_ci null default '' comment '创建者',
   `updater` varchar(64) character set utf8mb4 collate utf8mb4_unicode_ci null default '' comment '更新者',
   `deleted` bit(1) not null default b'0' comment '是否删除',
   `tenant_id` bigint not null default 0 comment '租户编号',

   `version` int default 0 not null comment '乐观锁',
   `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
   `create_time` datetime not null default current_timestamp comment '创建时间',
       primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_unicode_ci comment = '操作日志记录';