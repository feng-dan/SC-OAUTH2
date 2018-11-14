package com.adrian.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


/**
 * 资源服务器
 * auth-server提供user信息，所以auth-server也是一个Resource Server
 *
 * @author wangyunfei
 * @date 2017/6/9
 */
@Configuration
@EnableResourceServer
public class AuthorizationResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 默认的用户注册请求处理url
     */
    private static final String DEFAULT_SIGN_IN_USER_REGIST_URL = "/authentication/**";
    private static final String DEFAULT_SIGN_IN_USER_REVOKETOKEN_URL = "/oauth/token";

    @Autowired
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http    //关闭csrf(跨域攻击)验证
                .csrf().disable()
                //禁止服务器保存Session状态
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //异常处理
                .exceptionHandling()
                //身份验证入口点
                //.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                //访问拒绝处理程序
                .accessDeniedHandler(authenticationAccessDeniedHandler)
                .and()
                //多请求进行认证
                .authorizeRequests()
                //api
                .antMatchers("/swagger-ui.html","/v2/api-docs","/webjars/**","/configuration/**","/swagger-resources/**","/images/*").permitAll()
                //以已user/registered的POST请求都放行
                .antMatchers(HttpMethod.POST, DEFAULT_SIGN_IN_USER_REGIST_URL).permitAll()
                .antMatchers(HttpMethod.DELETE, DEFAULT_SIGN_IN_USER_REVOKETOKEN_URL).permitAll()
                //所以已“/”开始的请求都放行
                //.antMatchers("/*").permitAll()
                //访问/hello需要AUTH_WRITE权限
                //.antMatchers("/role").hasAuthority("user_management")
                //访问/world需要ADMIN角色
                .antMatchers("/world").hasRole("ADMIN")
                //其他所以请求需要身份认证
                .anyRequest().authenticated()
                .and()
                .httpBasic();


    }

}
