drop table if exists `doc_demo`;
create table `doc_demo`
(
    `id` bigint auto_increment comment '主键' primary key,
    `name_1` varchar(7) comment 'name1',
    `name_2` char(7) comment 'name2',
    `name_3` char(7) comment 'name3',
    `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime not null default current_timestamp comment '创建时间',
    index `联合索引`(`name_1`,`name_2`,`name_3`) using btree
)comment 'doc_demo';

insert into `doc_demo` values (1,"name_10","name_20","name_30",current_timestamp,current_timestamp);
insert into `doc_demo` values (2,"name_11","name_21","name_31",current_timestamp,current_timestamp);
insert into `doc_demo` values (3,"name_12","name_22","name_32",current_timestamp,current_timestamp);
insert into `doc_demo` values (4,"name_13","name_23","name_33",current_timestamp,current_timestamp);