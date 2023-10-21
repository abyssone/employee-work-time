package ru.abyssone.employeeworktime.entity;

import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.LocalDate;

public class WorkTimeReport {
    private Long id;
    private Contract contract;
    private LocalDate date;
    private TimePeriod workedTime;
}
