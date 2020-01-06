package com.syphan.practice.auth.util;

import com.syphan.practice.auth.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static final String DEFAULT_USER_ROLE = "ROLE_USER";
    public static final String BUILT_IN_ADMIN_ROLE = "ROLE_SUPER_ADMIN";

    public static UserPrincipal getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
        return UserPrincipal.builder()
                .id((Integer) additionalInfo.get("user_id"))
                .username((String) additionalInfo.get("user_name"))
                .avatar((String) additionalInfo.get("avatar"))
                .authorities(((List<String>) additionalInfo.get("authorities"))
                        .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                .build();
    }

    private static Map<String, Object> getAdditionalInfo(Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        return (Map<String, Object>) details.getDecodedDetails();
    }
}
