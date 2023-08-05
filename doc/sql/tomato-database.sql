/*
* 数据库
* MySQL8.x版本
*/
-- ----------------------------
-- 秒杀数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato_seckill` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
-- ----------------------------
-- 商品数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato-goods` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- 代码生成数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato-gen` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;


-- ----------------------------
-- 后台管理数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato-sys` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;


-- ----------------------------
-- 订单库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato_order_0` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS `tomato_order_1` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- 支付库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato-pay` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;


-- ----------------------------
-- 通知库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato_notice` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;


-- ----------------------------
-- 代理商
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato_agent` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;


-- ----------------------------
-- 账户库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato_account` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- 商户库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato_merchant` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- 授权中心
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato_oauth` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- 基于JDBC的事件发布
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato_modulith` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- 对账中心
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato_reconciliation` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;