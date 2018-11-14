# Spring Cloud下基于OAUTH2认证授权的实现

在`Spring Cloud`需要使用`OAUTH2`来实现多个微服务的统一认证授权，通过向`OAUTH服务`发送某个类型的`grant type`进行集中认证和授权，从而获得`access_token`，而这个token是受其他微服务信任的，我们在后续的访问可以通过`access_token`来进行，从而实现了微服务的统一认证授权。

本示例提供了四大部分：

- `discovery-service`:服务注册和发现的基本模块
- `auth-server`:OAUTH2认证授权中心
- `order-service`:普通微服务，用来验证认证和授权
- `api-gateway`:边界网关(所有微服务都在它之后)

OAUTH2中的角色：

- `Resource Server`:被授权访问的资源
- `Authotization Server`：OAUTH2认证授权中心
- `Resource Owner`： 用户
- `Client`：使用API的客户端(如Android 、IOS、web app)

Grant Type：

- `Authorization Code`:用在服务端应用之间
- `Implicit`:用在移动app或者web app(这些app是在用户的设备上的，如在手机上调起微信来进行认证授权)
- `Resource Owner Password Credentials(password)`:应用直接都是受信任的(都是由一家公司开发的，本例子使用)
- `Client Credentials`:用在应用API访问。

### 1.基础环境

使用`Postgres`作为账户存储，`Redis`作为`Token`存储，使用`docker-compose`在服务器上启动`Postgres`和`Redis`。
