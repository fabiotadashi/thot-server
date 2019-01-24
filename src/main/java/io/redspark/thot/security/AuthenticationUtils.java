package io.redspark.thot.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {
    public static String getAuthenticatedUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
