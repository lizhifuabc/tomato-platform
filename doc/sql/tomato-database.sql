/*
* 数据库
* MySQL8.x版本
*/
-- ----------------------------
-- 秒杀数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `tomato-seckill` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
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
CREATE DATABASE IF NOT EXISTS `tomato-order` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

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