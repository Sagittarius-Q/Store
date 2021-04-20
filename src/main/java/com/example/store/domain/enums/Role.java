package com.example.store.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    MODERATOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
