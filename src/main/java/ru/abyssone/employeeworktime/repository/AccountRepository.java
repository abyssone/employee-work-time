package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abyssone.employeeworktime.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
}
