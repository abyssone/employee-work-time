package ru.abyssone.employeeworktime.config;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "USER";
    }
}
