package com.syphan.practice.proxy.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

@Component
@ConfigurationProperties(prefix = "gateway", ignoreUnknownFields = false)
@Getter
@Setter
public class GatewayProperties {
    private CorsConfiguration cors = new CorsConfiguration();
}
