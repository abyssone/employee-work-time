package ru.abyssone.employeeworktime.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
public class Contract {
    private Long id;
    private Employee employee;
    private LocalDate dateOfConclusion;
    private LocalDate entryIntoForceDate;
    private Optional<LocalDate> expirationDate;
    // private ? exceptionalDays
    private String position;
    private WorkTimeModel workTimeModel;
    // private map<LocalDate, Rep> workTimeReports
}
