package com.syphan.practice.proxy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.syphan.common.api.handler", "com.syphan.practice.proxy.gateway"})
@EnableZuulProxy
@EnableFeignClients
public class ProxyGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProxyGatewayApplication.class, args);
    }
}
