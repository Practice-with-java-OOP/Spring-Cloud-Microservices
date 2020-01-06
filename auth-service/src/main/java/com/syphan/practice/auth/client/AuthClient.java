package com.syphan.practice.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @PostMapping("/oauth/token")
    OAuth2AccessToken getAccessToken(@RequestParam("grant_type") String grantType,
                                     @RequestParam("client_id") String clientId,
                                     @RequestParam("client_secret") String clientSecret,
                                     @RequestParam("username") String username,
                                     @RequestParam("password") String password);

}
