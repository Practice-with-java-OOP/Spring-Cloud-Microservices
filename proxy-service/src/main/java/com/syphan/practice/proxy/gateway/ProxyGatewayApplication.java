package com.syphan.practice.proxy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages = {"com.syphan.practice.proxy.gateway"})
@EnableZuulProxy
@EnableDiscoveryClient
public class ProxyGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProxyGatewayApplication.class, args);
    }
}
