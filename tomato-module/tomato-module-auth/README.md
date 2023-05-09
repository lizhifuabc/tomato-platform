# 核心源码

授权类型：
org.springframework.security.oauth2.core.AuthorizationGrantType

## OAuth2客户端规则配置

org.springframework.security.oauth2.server.authorization.settings.ClientSettings

- REQUIRE_PROOF_KEY 授权码授权流程中是否需要对密钥进行质询和验证，默认false。当为true时，开启授权码PKCE支持 RFC7636。
- REQUIRE_AUTHORIZATION_CONSENT 客户端请求授权时是否添加同意授权选项。
- JWK_SET_URL 这个参见Spring Security中的JOSE类库中相关的描述。
- TOKEN_ENDPOINT_AUTHENTICATION_SIGNING_ALGORITHM 为private_key_jwt和client_secret_jwt声明JWS签名算法。只能用于令牌端点对客户端进行身份验证环节。

客户端使用的身份验证方法：
org.springframework.security.oauth2.core.ClientAuthenticationMethod

1. client_secret_basic

   调用授权服务器token端点，添加Authorization header,值为client_id:client_secret Base64编码后的值，grant_type=client_credentials

2. client_secret_post

   调用授权服务器端点，在body添加grant_type=client_credentials、client_id=clientId、client_secret=clientSecret

3. client_secret_jwt

   使用客户端信息生成一个jwt_token,调用授权服务器token端点，参数client_assertion=jwt_token、client_assertion_type=‘urn:ietf:params:oauth:client-assertion-type:jwt-bearer’。

   jwt格式( Claim)：

   ```shell
   iss 必填. client_id.
   sub 必填. client_id
   aud 必填. token端点的url
   jti 必填. JWT ID
   exp 必填. 过期时间
   iat 可选. jwt生成时间
   ```
   
4. private_key_jwt

   在client_secret_jwt的基础上使用一个非对称秘钥对生成的jwt进行加密。

   


http://localhost:9000/.well-known/oauth-authorization-server