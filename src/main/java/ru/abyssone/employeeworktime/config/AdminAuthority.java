package ru.abyssone.employeeworktime.config;

import org.springframework.security.core.GrantedAuthority;

public class AdminAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ADMIN";
    }
}
