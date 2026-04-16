package com.hr_management.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtils {

    private UserDetailsCustom getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserDetailsCustom)) {
            throw new RuntimeException("Unauthorized");
        }
        return (UserDetailsCustom) auth.getPrincipal();
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public UUID getCurrentNhanSuId() {
        return getCurrentUser().getNhanSuId();
    }

    public String getUsername() {
        return getCurrentUser().getUsername();
    }
}
