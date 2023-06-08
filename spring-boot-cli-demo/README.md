# 使用maven构建自定义项目脚手架

1. mvn archetype:create-from-project 生成脚手架
2. 进入target/generated-sources/archetype目录下，可以看到脚手架已经生成，执行 mvn clean install 安装到本地仓库
3. 查看本地 Maven 仓库，是否存在 archetype-catalog.xml文件

脚手架就安装成功。

## 创建新的项目

1. 创建空白目录
2. mvn archetype:generate  -DarchetypeCatalog=local