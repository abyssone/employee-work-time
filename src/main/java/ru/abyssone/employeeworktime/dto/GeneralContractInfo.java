package ru.abyssone.employeeworktime.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class GeneralContractInfo {
    private UUID id;
    private String position;
    private LocalDate dateOfConclusion;
    private ContractState state;
    private Optional<LocalDate> expirationDate;
}
