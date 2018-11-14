package com.adrian.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Redis用来存储token，服务重启后，无需重新获取token.
 * 认证服务器配置
 *
 * @author fengdan
 * @date 2018/10/19
 */
@Configuration
@EnableAuthorizationServer
public class AdrianAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * clientId
     */
    private static final String IBOUT_SECURITY_OAUTH2_CLIENTS_CLIENTID = "adrian";
    /**
     * clientSecret
     */
    private static final String IBOUT_SECURITY_OAUTH2_CLIENTS_CLIENTSECRET = "adrian";

    /**
     * scopes
     */
    private static final String IBOUT_SECURITY_OAUTH2_CLIENTS_SCOPES = "all";
    /**
     * token有效期自定义设置，默认12小时
     * accessTokenValidateSeconds
     */
    private static final int IBOUT_SECURITY_OAUTH2_CLIENTS_ACCESSTOKENVALIDATESECONDS = 60 * 60 * 12;

    /**
     * //默认30天，这里修改
     * REFRESHTOKEN
     */
    private static final int IBOUT_SECURITY_OAUTH2_CLIENTS_REFRESHTOKEN = 60 * 60 * 24 * 7;
    /**
     * 认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 存储链接
     */
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * token存放位置
     *
     * @return RedisTokenStore
     */
    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }


    /**
     * 配置认证管理器以及用户信息业务实现
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //认证管理
                .authenticationManager(authenticationManager)
                //若无，refresh_token会有UserDetailsService is required错误
                //用户信息业务实现
                .userDetailsService(userDetailsService)
                //令牌存储
                .tokenStore(tokenStore());
    }

    /**
     * 配置认证规则，那些需要认证不需要认证
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //令牌密钥访问
                .tokenKeyAccess("permitAll()")
                //检查令牌访问
                .checkTokenAccess("isAuthenticated()")
                //允许客户端的表单身份验证
                .allowFormAuthenticationForClients();

    }

    /**
     * 配置客户端
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端账户
                .withClient(IBOUT_SECURITY_OAUTH2_CLIENTS_CLIENTID)
                //作用域/限制允许的权限配置
                .scopes(IBOUT_SECURITY_OAUTH2_CLIENTS_SCOPES)
                //客户端密码
                .secret(IBOUT_SECURITY_OAUTH2_CLIENTS_CLIENTSECRET)
                //刷新令牌有效期秒
                .refreshTokenValiditySeconds(IBOUT_SECURITY_OAUTH2_CLIENTS_REFRESHTOKEN)
                //访问令牌有效性秒
                .accessTokenValiditySeconds(IBOUT_SECURITY_OAUTH2_CLIENTS_ACCESSTOKENVALIDATESECONDS)
                //授权方式
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                //不同的客户端连接
                .and()
                //客户端账户
                .withClient("webapp")
                //客户端密码
                .secret("_webapp_clientId_list_")
                //刷新令牌有效期秒
                .refreshTokenValiditySeconds(IBOUT_SECURITY_OAUTH2_CLIENTS_REFRESHTOKEN)
                //访问令牌有效性秒
                .accessTokenValiditySeconds(IBOUT_SECURITY_OAUTH2_CLIENTS_ACCESSTOKENVALIDATESECONDS)
                //作用域/限制允许的权限配置
                .scopes(IBOUT_SECURITY_OAUTH2_CLIENTS_SCOPES)
                .authorizedGrantTypes("password", "authorization_code", "refresh_token");
    }


}
