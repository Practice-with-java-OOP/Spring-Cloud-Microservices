package com.syphan.practice.proxy.gateway.controller;

import com.syphan.practice.proxy.gateway.config.JwtTokenProperties;
import com.syphan.practice.proxy.gateway.swaggerConfig.AdditionalTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GatewayController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtTokenProperties jwtTokenProperties;

    @GetMapping("/test")
    public String test() {
        String token = request.getHeader("Authorization");
        AdditionalTokenUtils.verifyToken(token.substring(jwtTokenProperties.getTokenPrefix().length() + 1, token.length()), jwtTokenProperties.getSecret());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
//        int userId = (int) additionalInfo.get("user_id");
        return "oke nhe";
    }

//    private Map<String, Object> getAdditionalInfo(Authentication authentication) {
////        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//        return (Map<String, Object>) details.getDecodedDetails();
//    }
}
