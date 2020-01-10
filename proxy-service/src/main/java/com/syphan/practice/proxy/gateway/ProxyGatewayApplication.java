package com.syphan.practice.proxy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages = {"com.syphan.common.rest", "com.syphan.practice.proxy.gateway"})
@EnableZuulProxy
public class ProxyGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProxyGatewayApplication.class, args);
    }
}
