在 DDD 中，Domain 层是业务逻辑的核心所在，它应该包含与业务相关的所有领域模型、实体、值对象、服务、仓储接口以及领域事件等内容。Domain 层的目标是实现业务逻辑的领域驱动设计，并且将其与技术细节分离，以便更好地保持代码质量、可测试性和可维护性。

具体而言，可以按照以下方式进行分层：

- Entity 层：包含领域实体和值对象等，表示业务中的实际物体或概念。例如，对于电商业务，可以有 Order、Product、Customer 等实体。
- Repository 层：包含领域仓储接口，用于管理实体的存储和检索。例如，可以定义 OrderRepository、ProductRepository、CustomerRepository 等接口，用于管理相应实体的持久化操作。
- Service 层：包含领域服务，表示一些无法完全归属于某个实体或值对象的业务逻辑。例如，可以定义 ShoppingCartService、PaymentService 等服务，用于处理购物车、支付等相关业务。
- Event 层：包含领域事件，表示领域模型中发生的一些重要事件，如订单创建、支付成功等。可以通过事件驱动的方式实现领域模型的解耦。

需要注意的是，领域模型应该尽可能地贴近实际业务，避免过度抽象和技术实现细节的泄露。此外，为了保证 Domain 层的独立性和可测试性，应该尽可能避免对 Infrastracture 层的依赖，例如数据库、MQ 等技术实现细节，可以通过 Repository 和 Service 接口的方式暴露必要的操作，由 Infrastracture 层进行实现。