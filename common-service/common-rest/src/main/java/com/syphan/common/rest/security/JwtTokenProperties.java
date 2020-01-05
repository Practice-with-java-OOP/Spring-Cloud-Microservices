package com.syphan.common.rest.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.jwt")
public class JwtTokenProperties {
    private String secret;

    private Duration avlPeriod;

    private String header;

    private String tokenPrefix;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Duration getAvlPeriod() {
        return avlPeriod;
    }

    public void setAvlPeriod(Duration avlPeriod) {
        this.avlPeriod = avlPeriod;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }
}
