package com.adrian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Spring Security默认是禁用注解的，要想开启注解
 * #@EnableGlobalMethodSecurity(prePostEnabled = true)注解，来判断用户对某个控制层的方法是否具有访问权限
 * @author asus
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
