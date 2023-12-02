package ru.abyssone.employeeworktime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.dto.UserAccountDetails;
import ru.abyssone.employeeworktime.entity.Account;
import ru.abyssone.employeeworktime.mapper.AccountMapper;
import ru.abyssone.employeeworktime.repository.AccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) throw new UsernameNotFoundException("Username is null");

        Optional<Account> account = accountRepository.findById(username);
        if (account.isEmpty()) throw new UsernameNotFoundException(String.format(
                "Account with username: %s not found", username
        ));

        UserAccountDetails userAccountDetails = accountMapper.toUserAccountDetails(account.get());
        return userAccountDetails;
    }
}
