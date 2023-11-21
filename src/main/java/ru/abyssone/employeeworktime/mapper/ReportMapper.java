package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import ru.abyssone.employeeworktime.dto.DailyWorkReport;
import ru.abyssone.employeeworktime.entity.AbsenceReason;
import ru.abyssone.employeeworktime.entity.WorkTimeReport;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Mapper
public abstract class ReportMapper {

    protected enum TimeStatus {
        MISSED, OVERTIME
    }

    public List<DailyWorkReport> toDailyWorkReports(Map<LocalDate, TimePeriod> scheduledWorkTime,
                                                    Map<LocalDate, WorkTimeReport> reports) {
        List<DailyWorkReport> dailyWorkReports = new ArrayList<>();

        Set<LocalDate> dates = scheduledWorkTime.keySet();

        for (LocalDate date : dates) {
            DailyWorkReport workReport = new DailyWorkReport();
            TimePeriod scheduledTime = scheduledWorkTime.get(date);
            TimePeriod actualWorkedTime = reports.containsKey(date) ? reports.get(date).getWorkedTime() : null;

            workReport.setDate(date);

            workReport.setScheduledTime(scheduledTime != null ? Optional.of(scheduledTime) : Optional.empty());
            workReport.setActualWorkedTime(actualWorkedTime != null ? Optional.of(actualWorkedTime) : Optional.empty());

            Map<TimeStatus, Duration> timeDifference = this.getTimeDifference(scheduledTime, actualWorkedTime);

            workReport.setMissedTime(this.minutesToTimeFormat(timeDifference.get(TimeStatus.MISSED).toMinutes()));
            workReport.setOvertime(this.minutesToTimeFormat(timeDifference.get(TimeStatus.OVERTIME).toMinutes()));

            if (reports.containsKey(date)) {
                workReport.setAbsenceReason(reports.get(date).getAbsenceReason());
            } else {
                workReport.setAbsenceReason(AbsenceReason.NO_REASON);
            }
            dailyWorkReports.add(workReport);
        }

        return dailyWorkReports;
    }

    protected Map<TimeStatus, Duration> getTimeDifference(TimePeriod scheduledTime, TimePeriod actualTime) {
        Map<TimeStatus, Duration> timeDiff = new HashMap<>() {{
            put(TimeStatus.MISSED, Duration.ZERO);
            put(TimeStatus.OVERTIME, Duration.ZERO);
        }};

        // Если рабочее время по графику = null, то OVERTIME = все фактическое время работы

        if (scheduledTime == null) {
            timeDiff.put(TimeStatus.OVERTIME, actualTime == null
                    ? Duration.ZERO
                    : Duration.between(actualTime.getStartTime(), actualTime.getEndTime()));
            timeDiff.put(TimeStatus.MISSED, Duration.ZERO);
            return timeDiff;
        }

        // Если фактическое время работы = null, то MISSED TIME = все рабочее время по графику

        if (actualTime == null) {
            timeDiff.put(TimeStatus.MISSED, scheduledTime == null
                    ? Duration.ZERO
                    : Duration.between(scheduledTime.getStartTime(), scheduledTime.getEndTime()));
            timeDiff.put(TimeStatus.OVERTIME, Duration.ZERO);
            return timeDiff;
        }

        LocalTime scheduledStartTime = scheduledTime.getStartTime();
        LocalTime scheduledEndTime = scheduledTime.getEndTime();

        LocalTime actualStartTime = actualTime.getStartTime();
        LocalTime actualEndTime = actualTime.getEndTime();

        // если работа началась позже графика: += MISSED TIME
        // иначе: += OVERTIME

        if (scheduledStartTime.isBefore(actualStartTime)) {
            timeDiff.put(TimeStatus.MISSED,
                    timeDiff.get(TimeStatus.MISSED).plus(Duration.between(scheduledStartTime, actualStartTime)));
        } else {
            timeDiff.put(TimeStatus.OVERTIME,
                    timeDiff.get(TimeStatus.OVERTIME).plus(Duration.between(actualStartTime, scheduledStartTime)));
        }

        // если работа завершилась раньше графика: += MISSED TIME
        // иначе: += OVERTIME

        if (scheduledEndTime.isAfter(actualEndTime)) {
            timeDiff.put(TimeStatus.MISSED,
                    timeDiff.get(TimeStatus.MISSED).plus(Duration.between(actualEndTime, scheduledEndTime)));
        } else {
            timeDiff.put(TimeStatus.OVERTIME,
                    timeDiff.get(TimeStatus.OVERTIME).plus(Duration.between(scheduledEndTime, actualEndTime)));
        }

        return timeDiff;
    }

    protected String minutesToTimeFormat(long minutes) {
        return String.format("%d:%02d", minutes/60, minutes%60);
    }
}
