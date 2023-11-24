package ru.abyssone.employeeworktime.dto.employee;

import lombok.Getter;
import lombok.Setter;
import ru.abyssone.employeeworktime.dto.schedule.ScheduleDescription;
import ru.abyssone.employeeworktime.dto.contract.GeneralContractInfo;

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
