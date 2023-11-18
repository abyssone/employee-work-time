package ru.abyssone.employeeworktime.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class GeneralEmployeeInfo {
    private UUID id;
    private String name;
    private Optional<GeneralContractInfo> contract;
}
