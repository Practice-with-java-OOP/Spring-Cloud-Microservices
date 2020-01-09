package com.syphan.practice.proxy.gateway.config;

import com.syphan.practice.proxy.gateway.filter.CustomPreFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CustomPreFilter customPreFilter() {
        return new CustomPreFilter();
    }
}
