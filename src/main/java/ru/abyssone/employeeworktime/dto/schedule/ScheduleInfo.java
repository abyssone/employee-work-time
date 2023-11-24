package ru.abyssone.employeeworktime.dto.schedule;

import lombok.Getter;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class ScheduleInfo {
    UUID id;
    ScheduleType type;
    String title;
    LocalDate startWorkSchedule;
    Integer workDaysNumber;
    Integer daysOffNumber;
    TimePeriod workHours;
    Map<DayOfWeek, TimePeriod> workWeekHours;
}