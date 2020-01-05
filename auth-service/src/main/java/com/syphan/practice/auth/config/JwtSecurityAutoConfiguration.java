package com.syphan.practice.auth.config;

import com.syphan.practice.auth.security.JwtTokenProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtTokenProperties.class)
public class JwtSecurityAutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean(name = {"customUserDetailsService"})
//    public CustomUserDetailsService customUserDetailsService() {
//        return new CustomUserDetailsServiceImpl();
//    }
}
