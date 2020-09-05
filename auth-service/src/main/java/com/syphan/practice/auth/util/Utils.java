package com.syphan.practice.auth.util;

import com.syphan.common.rest.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    public static final String DEFAULT_USER_ROLE = "ROLE_USER";
    public static final String BUILT_IN_ADMIN_ROLE = "ROLE_SUPER_ADMIN";

    public static UserPrincipal getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal) authentication.getPrincipal();
    }
}
