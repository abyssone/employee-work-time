package ru.abyssone.employeeworktime.entity;

import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.LocalDate;

public class Contract {
    private LocalDate dateOfConclusion;
    private LocalDate entryIntoForceDate;
    private LocalDate expirationDate;
    private String position;
    private WorkTimeModel workTimeModel;
}
