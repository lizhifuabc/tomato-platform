# 分片组
# 分片数量一但确定，为了不影响后续的水平扩容，不允许改变
drop table if exists `t_shard_group`;
create table`t_shard_group` (
  `id` bigint(20) unsigned not null auto_increment,
  `shard_group_name` varchar(36) not null  comment '分片组名称',
  `shard_count` int not null  comment '分片数量',
  primary key (`id`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分片组';
insert into `t_shard_group` values (1000,'普通组',1024);
insert into `t_shard_group` values (1001,'大商户组',1024);

# 通过将分布Key上值进行Hash路由到各个DataNode
# 1. 数据分布关键字（Distribute Key）先被Hash出ShardId
# 2. 用ShardId查询ShardMap表找到数据对应的DataNode
#
# shardmap 维护 ShardId 对应的 DataNode
# shardmap 指一种逻辑映射关系的管理视图，负责将业务数据依照分布键值计算的中间结果值映射到确定的存储节点。
drop table if exists `shardmap`;
create table`shardmap` (
  `id` bigint(20) unsigned not null auto_increment,
  `ShardId` bigint(0) NOT NULL COMMENT 'ShardId（分片字段的hash值）',
  `DataNode`  varchar(36) not null  comment '数据节点编号',
  primary key (`id`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'shardmap';
insert into `shardmap` values (1,1,'ds0');
insert into `shardmap` values (2,2,'ds1');

# 分片结果数据节点映射 shardmap
drop table if exists `t_shard_set`;
create table`t_shard_set` (
  `id` bigint(20) unsigned not null auto_increment,
  `shard_group_id` bigint(0) NOT NULL COMMENT '分片组ID',
  `shard_db`  varchar(36) not null  comment '数据节点编号',
  `shard_key` varchar(36) not null  comment '分片范围',
  primary key (`id`) using btree,
  unique key (`shard_group_id`,`shard_db`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分片结果数据节点映射';
insert into `t_shard_set` values (1000,1000,'ds0','0-511');
insert into `t_shard_set` values (1000,1000,'ds1','512-1023');

insert into `t_shard_set` values (1000,1001,'ds2','0-511');
insert into `t_shard_set` values (1000,1001,'ds3','512-1023');

# 例如扩容大商户组物理节点
# ds2：0-511
# ds3：512-1023
# 扩容后：
# 1.需要将ds2的数据同步到ds4，数据校验
# 2.中断业务，等待数据追平
# 3.切换路由，
# 4.删除ds4冗余数据（后续处理）
# ds2：0-255
# ds3：512-1023
# ds4：255-511


# 商户分片配置
# 通过在大用户 group 分布逻辑中加入日期偏移，来实现同一个用户的数据在 group 内部多个节点间均匀分布，从而有效解决数据分布不均匀问题。
# Shardid = Hash(merchantid) % #shard_count + fcreate_timedayoffset from 1970-01-01
# Shardid = Hash(merchantid) % #shard_count
drop table if exists `t_shard_merchant`;
create table`t_shard_merchant` (
  `id` bigint(20) unsigned not null auto_increment,
  `shard_group_id` bigint(0) NOT NULL COMMENT '分片组ID',
  `merchant_no` varchar(36) not null  comment '商编',
  `from_create_time` datetime not null comment '开始时间',
  primary key (`id`) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商户分片配置';
insert into `t_shard_merchant` values (1000,1001,'1000000','2022-12-31 00:00:00');