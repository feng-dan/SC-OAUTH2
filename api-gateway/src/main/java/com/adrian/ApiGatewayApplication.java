package com.adrian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 在spring cloud Edgware 以及更高版本中，只需要添加相关依赖，即可自动注册.
 * EnableDiscoveryClient(autoRegister =false) 此服务不注册到Eureka Server
 * @author asus
 */
@EnableFeignClients
@EnableZuulProxy
@EnableOAuth2Sso
@SpringCloudApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
