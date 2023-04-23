# Git使用规范



## 提交日志

提交信息：`type(scope[optional]): subject`

### 1. type

- feature：新功能
- fix：修补 bug
- docs：文档
- style：格式（不影响代码运行的变动）
- refactor：重构（即不是新增功能，也不是修改 bug 的代码变动）
- test：增加测试
- chore：构建过程或辅助工具的变动
- optimize：优化

### 2. scope

scope 用于说明 commit 影响的范围，比如数据层、控制层、视图层等等，视项目不同而不同。

### 3. subject

subject 是 commit 目的的简短描述，不超过 50 个字符。

- 以动词开头，使用第一人称现在时，比如 change，而不是 changed 或 changes
- 第一个字母小写
- 结尾不加句号（.）

示例：

feature(message-center): 开发消息发送落库功能。或者 feature: 开发消息发送落库功能。

## 分支命名规范

分支命名：`{type}_{time}_{describe}_{developer}`

### 1. type

- feature：新功能
- fix：修补 bug
- docs：文档
- style：格式（不影响代码运行的变动）
- refactor：重构（即不是新增功能，也不是修改 bug 的代码变动）
- test：增加测试
- chore：构建过程或辅助工具的变动
- optimize：优化

###  2. time

年月日方式（20230423）

###  3. describe

例如消息发送：send-message。尽量以两个单词描述清楚，多个单词间使用中划线 - 分割。

###  4. developer

建议填写开发者名字，例如：engine.li

示例：

feature_20220915_send-message_engine.li