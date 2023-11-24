package ru.abyssone.employeeworktime.dto.contract;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class FullContractInfo {
    private UUID id;
    private UUID employeeId;
    private UUID scheduleId;
    private String position;
    private LocalDate dateOfConclusion;
    private LocalDate expirationDate;
}
