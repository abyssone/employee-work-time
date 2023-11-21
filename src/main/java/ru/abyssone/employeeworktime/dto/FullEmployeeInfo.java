package ru.abyssone.employeeworktime.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class FullEmployeeInfo {
    private UUID id;
    private String name;
    private Optional<GeneralContractInfo> contract;
    private Optional<ScheduleDescription> schedule;
}
