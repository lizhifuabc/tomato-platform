INSERT INTO `t_sys_user` (`enabled`, `credentials_expire_at`, `user_id`, `username`, `password`) VALUES (1, '2223-06-09 15:11:17', 1,'admin', '{bcrypt}$2a$10$wvku73Pz2.8hlbpAHWlpseN1xbdc9qfaJgNGRXh6Vsz.pu7//4dCy');

insert into `t_sys_role` (`role_id`,`role_code`,`role_name`,`role_status`) values (1,'ROLE_ADMIN','管理员权限',1);

insert into `t_sys_user_role` (`user_id`,`role_id`) values (1,1);

insert into `t_sys_permission` (`permission_id`,`permission_code`,`permission_name`,`parent_id`,`sort`,`permission_type`,`create_by`,`create_time`) values (1,'sys:user:add','创建用户',0,1,1,'SYS',now());
insert into `t_sys_permission` (`permission_id`,`permission_code`,`permission_name`,`parent_id`,`sort`,`permission_type`,`create_by`,`create_time`) values (2,'sys:user:updatePassword','修改密码',0,2,1,'SYS',now());

insert into `t_sys_role_permission` (`role_id`,`permission_id`) values (1,1);
insert into `t_sys_role_permission` (`role_id`,`permission_id`) values (1,2);