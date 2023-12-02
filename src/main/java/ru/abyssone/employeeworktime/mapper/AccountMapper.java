package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import ru.abyssone.employeeworktime.dto.UserAccountDetails;
import ru.abyssone.employeeworktime.entity.Account;

@Mapper
public interface AccountMapper {
    UserAccountDetails toUserAccountDetails(Account account);
}
