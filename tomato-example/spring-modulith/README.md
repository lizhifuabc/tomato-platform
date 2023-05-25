# Spring Modulith

https://docs.spring.io/spring-modulith/docs/current/reference/html/
https://github.com/spring-projects/spring-modulith



Spring Module 是 Spring 官方提供的一套用于构建大型 Spring 应用的模块化方案。它提供了以下主要功能:

1. 按模块划分 Spring 应用我们可以将一个大型 Spring 应用划分为多个模块(Module),每个模块都有自己的容器配置、bean 定义、资源等。
2. 模块之间松散耦合模块之间通过指定对其他模块的依赖来保持松散耦合。Spring会负责在启动时加载模块间的依赖关系。
3. 统一的配置加载Spring Module 会从各模块加载配置,并将所有配置加载到一个统一的 Spring ApplicationContext 中。
4. Bean 依赖注入跨模块通过在模块间指定依赖,一个模块中定义的Bean可以被其他依赖模块的Bean进行注入使用。

## Spring Modulith 的主要思想（AI）

1. 模块化设计：将应用程序划分为多个模块，每个模块都具有特定的职责和功能。每个模块可以是一个独立的 Maven/Gradle 模块，可以包含自己的业务逻辑、数据访问和依赖项。
2. 显式依赖：模块之间的依赖关系是显式声明的，通过 Maven/Gradle 的依赖关系来管理。这样可以明确地了解模块之间的关系，避免隐式的依赖关系导致的复杂性和不可预测性。
3. 清晰的边界：每个模块都有明确的边界，尽量避免模块之间的相互依赖和混淆。模块之间的通信可以通过接口、事件或消息进行。
4. 通用基础设施：Spring Modulith 提倡将通用的基础设施和功能提取到共享的模块中，例如安全性、日志记录、配置等。这样可以避免每个模块都重复实现相同的功能。
5. 单一部署单元：尽可能将模块打包为一个单一的部署单元，以便于部署和管理。这样可以简化部署和水平扩展，并提供更好的运行时可见性。
