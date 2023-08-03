# SQL 使用

## 建表

1. `engine = innodb`: 指定表的存储引擎为InnoDB。InnoDB是MySQL的一种存储引擎，它支持事务和外键等高级特性，通常用于需要事务支持的场景。
2. `auto_increment = 1`: 指定自增长的起始值为1。在插入数据时，如果没有显式指定自增长列的值，系统会自动分配一个新的唯一值，起始值为1，后续每次自增1。
3. `character set = utf8mb4`: 指定表的字符集为utf8mb4，即支持4字节的Unicode字符。这样可以存储包含Emoji表情等特殊字符的数据。
4. `collate = utf8mb4_unicode_ci`: 指定表的排序规则为utf8mb4_unicode_ci，这是一种区分大小写并且支持多语言的排序规则。
5. `comment = '订单表'`: 给表添加了一个注释，注释内容为'订单表'，可以用于描述表的作用或用途。
6. `row_format = dynamic`: 指定行格式为dynamic，这是InnoDB引擎的一种行格式，支持变长行，能够有效节省存储空间。
