package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Table(name = "accounts")
@Entity
@NoArgsConstructor
@Setter
@Getter
public class Account {

    public enum Role implements GrantedAuthority {
        ROLE_USER,
        ROLE_STAFF,
        ROLE_ADMIN;
        @Override
        public String getAuthority() {
            return name();
        }
    }

    @Id
    private String username;

    private String password;

    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
