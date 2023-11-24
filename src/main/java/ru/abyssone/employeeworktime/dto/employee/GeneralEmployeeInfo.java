package ru.abyssone.employeeworktime.dto.employee;

import lombok.Getter;
import lombok.Setter;
import ru.abyssone.employeeworktime.dto.contract.GeneralContractInfo;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class GeneralEmployeeInfo {
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private String address;
    private String sex;
    private Optional<GeneralContractInfo> contract;
}
