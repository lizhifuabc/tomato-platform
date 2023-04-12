## 授权服务器地址

http://localhost:9000/.well-known/openid-configuration

.well-known/openid-configuration 是 OpenID Connect 标准中定义的一个 URL，用于获取 OpenID Connect Provider 的元数据信息。通过访问 .well-known/openid-configuration URL，客户端可以获取到授权服务器提供的 OpenID Connect 元数据，包括以下信息：

issuer：授权服务器的 issuer，即授权服务器的唯一标识。

authorization_endpoint：授权服务器的授权端点 URL，客户端可以通过该 URL 进行用户授权。

token_endpoint：授权服务器的令牌端点 URL，客户端可以通过该 URL 获取访问令牌和刷新令牌。

userinfo_endpoint：授权服务器的用户信息端点 URL，客户端可以通过该 URL 获取用户的个人信息。

jwks_uri：授权服务器的 JSON Web Key Set 端点 URL，客户端可以通过该 URL 获取授权服务器的公钥信息，用于验证 ID Token 的签名。

scopes_supported：授权服务器支持的授权范围。

response_types_supported：授权服务器支持的响应类型。

subject_types_supported：授权服务器支持的 subject 类型。

id_token_signing_alg_values_supported：授权服务器支持的 ID Token 签名算法。

token_endpoint_auth_methods_supported：授权服务器支持的令牌端点身份验证方法。

claims_supported：授权服务器支持的声明。
通过获取这些元数据信息，客户端可以了解授权服务器的配置信息，从而更加方便地实现 OpenID Connect 认证流程。

