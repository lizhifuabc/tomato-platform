# 幂等设计

使用相同参数对同一资源重复调用某个接口或方法的结果与调用一次的结果相同。

## 数据库CRUD

R（read）： SQL语句为select。只要查询条件不变，本身是幂等的，不需要再做处理。

C（create）： SQL语句为insert。insert前做业务防重或是在数据库表上对业务主键加唯一索引。

U（update）： 对应的操作SQL语句为update。更新操作时，一定是要用绝对值进行更新操作，而不要用相对值进行更新，相对值更新可能导致更新操作不幂等。

例如：

```sql
-- 幂等
update user set age = 10 where id = 1;
-- 非幂等
update user set age++ where id = 1;
```

D（delete）：SQL语句为delete。推荐：先按范围查询，再按查询的主键进行删除。

例如：

```sql
-- 幂等
delete from user where id = 1;
-- 非幂等
delete from user where id in （select id from user order by id desc limit 10);
```

常见机制：

1. **页面token机制：** 进入页面时，从服务器获取token，在服务器端把token进行存储，提交时把token带到服务器端进行验证；
2. **乐观锁机制：**使用数据库的版本号实现乐观锁，数据库更新时，判断版本号是否与查询时保持一致，一致更新成功，否则更新失败；
3. **select+insert**，先查询后插入，常用于并发不高的一些后台系统或是防止任务的重复执行；
4. **悲观锁机制**，一般id为主键或唯一索引，仅锁定当前记录（select * from table where id = '1234' for update;）
5. **去重表**，去重表做唯一性索引，先查询，再操作业务表。
6. **数据库唯一索引**，为业务表建立唯一索引，避免业务数据多次写入；
7. **状态机**，业务状态在变更之前是有条件的，必须按设定的状态条件进行更新；

## 接口防刷，使用频率限制

和幂等性的原理类似，只不过是限制的是接口而不是参数。
