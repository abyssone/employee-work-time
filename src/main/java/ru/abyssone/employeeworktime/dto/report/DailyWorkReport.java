package ru.abyssone.employeeworktime.dto.report;

import lombok.Getter;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.AbsenceReason;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class DailyWorkReport {
    private LocalDate date;
    private Optional<TimePeriod> scheduledTime;
    private Optional<TimePeriod> actualWorkedTime;
    private String missedTime;
    private String overtime;
    private AbsenceReason absenceReason;
}
