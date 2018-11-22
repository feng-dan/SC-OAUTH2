package com.adrian;

import com.adrian.security.SecurityUtils;
import com.adrian.repository.support.WiselyRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author asus
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(repositoryBaseClass = WiselyRepositoryImpl.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthServerApplication {

    @Bean(name = "auditorAware")
    public AuditorAware<String> auditorAware() {
        return SecurityUtils::getCurrentUserUsername;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }


}
