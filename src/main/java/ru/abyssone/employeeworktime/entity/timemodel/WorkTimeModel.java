package ru.abyssone.employeeworktime.entity.timemodel;

import java.time.LocalDate;
import java.util.Map;

public abstract class WorkTimeModel {

    public abstract Integer getWorkHours(LocalDate date);
    public abstract Map<LocalDate, Integer> getWorkHours(LocalDate start, LocalDate end);
}
