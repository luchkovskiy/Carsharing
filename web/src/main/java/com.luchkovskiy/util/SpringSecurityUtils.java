package com.luchkovskiy.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class SpringSecurityUtils {

    public void logout(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Principal object is empty");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && principal.getName().equals(authentication.getName())) {
            SecurityContextHolder.clearContext();
        } else {
            throw new RuntimeException("Security context is clear");
        }
    }
}
