package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import ru.abyssone.employeeworktime.dto.report.DailyWorkReport;
import ru.abyssone.employeeworktime.dto.report.FullWorkReportsStatistic;
import ru.abyssone.employeeworktime.dto.report.WorkTimeReportInfo;
import ru.abyssone.employeeworktime.entity.AbsenceReason;
import ru.abyssone.employeeworktime.entity.WorkTimeReport;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static javax.swing.UIManager.put;

@Mapper
public abstract class ReportMapper {

    protected enum TimeStatus {
        MISSED, OVERTIME
    }

    public FullWorkReportsStatistic toFullWorkReportsStatistic(Map<LocalDate, TimePeriod> scheduledWorkTime,
                                                               Map<LocalDate, WorkTimeReport> reports) {

        FullWorkReportsStatistic statistic = new FullWorkReportsStatistic();
        List<DailyWorkReport> resultReports = new ArrayList<>();

        /*
         * Часы отсутствия и переработки за весь диапазон выборки
         */
        long totalMissedMinutes = 0l;
        long totalOvertimeMinutes = 0l;

        /*
         * Часы отсутствия с указанием причины отсутствия за весь диапазон выборки
         */
        Map<AbsenceReason, Long> missedMinutesForReason = new HashMap<>(){{
            put(AbsenceReason.NO_REASON, 0l);
            put(AbsenceReason.SICK_LEAVE, 0l);
            put(AbsenceReason.BUSINESS_TRIP, 0l);
        }};

        Set<LocalDate> dates = scheduledWorkTime.keySet();

        for (LocalDate date : dates) {
            TimePeriod scheduledTime = scheduledWorkTime.get(date);
            TimePeriod actualWorkedTime = reports.containsKey(date) ? reports.get(date).getWorkedTime() : null;

            DailyWorkReport workReport = new DailyWorkReport();

            workReport.setDate(date);

            workReport.setScheduledTime(scheduledTime != null ? Optional.of(scheduledTime) : Optional.empty());
            workReport.setActualWorkedTime(actualWorkedTime != null ? Optional.of(actualWorkedTime) : Optional.empty());

            // Вычисление разницы между рабочими часами по графику и рабочими часами в отчете сотрудника
            Map<TimeStatus, Duration> timeDifference = this.getTimeDifference(scheduledTime, actualWorkedTime);

            totalMissedMinutes = totalMissedMinutes + timeDifference.get(TimeStatus.MISSED).toMinutes();
            totalOvertimeMinutes = totalOvertimeMinutes + timeDifference.get(TimeStatus.OVERTIME).toMinutes();

            workReport.setMissedTime(this.minutesToTimeFormat(timeDifference.get(TimeStatus.MISSED).toMinutes()));
            workReport.setOvertime(this.minutesToTimeFormat(timeDifference.get(TimeStatus.OVERTIME).toMinutes()));

            AbsenceReason reason;

            // Если в бд существует отчет за контретную дату, то причина возможного отсутсвия берется из отчета,
            // иначе - NO_REASON по умолчанию.
            if (reports.containsKey(date)) {
                reason = reports.get(date).getAbsenceReason();
                missedMinutesForReason.put(reason,
                        missedMinutesForReason.get(reason) + timeDifference.get(TimeStatus.MISSED).toMinutes());
                workReport.setAbsenceReason(reason);
            } else {
                reason = AbsenceReason.NO_REASON;
                missedMinutesForReason.put(reason,
                        missedMinutesForReason.get(reason) + timeDifference.get(TimeStatus.MISSED).toMinutes());
                workReport.setAbsenceReason(reason);
            }
            resultReports.add(workReport);
        }

        statistic.setTotalMissed(this.minutesToTimeFormat(totalMissedMinutes));
        statistic.setTotalOvertime(this.minutesToTimeFormat(totalOvertimeMinutes));

        for(Map.Entry<AbsenceReason, Long> entry : missedMinutesForReason.entrySet()) {
            statistic.getMissedForReason().put(entry.getKey(), this.minutesToTimeFormat(entry.getValue()));
        }

        statistic.setReports(resultReports.stream().sorted(
                (a, b) -> {
                    return a.getDate().isBefore(b.getDate()) ? -1 : b.getDate().isBefore(a.getDate()) ? 1 : 0;
                }).collect(Collectors.toList()));

        return statistic;
    }

    /*
     * Вычисление разницы между рабочими часами по графику и фактическому времени работы сотрудника.
     * Возвращает Map с часами отстутсвия/переработки
     */
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

        // Начало графика раньше чем начало рабочего времени сотрудника
        if (scheduledStartTime.isBefore(actualStartTime)) {
            timeDiff.put(TimeStatus.MISSED,
                    timeDiff.get(TimeStatus.MISSED).plus(Duration.between(
                            scheduledStartTime,
                            actualStartTime.isBefore(scheduledEndTime) ? actualStartTime : scheduledEndTime)));
        } else {
            timeDiff.put(TimeStatus.OVERTIME,
                    timeDiff.get(TimeStatus.OVERTIME).plus(Duration.between(
                            actualStartTime,
                            scheduledStartTime.isBefore(actualEndTime) ? scheduledStartTime : actualEndTime)));
        }

        // если работа завершилась раньше графика: += MISSED TIME
        // иначе: += OVERTIME

        if (scheduledEndTime.isAfter(actualEndTime)) {
            timeDiff.put(TimeStatus.MISSED,
                    timeDiff.get(TimeStatus.MISSED).plus(Duration.between(
                            actualEndTime.isAfter(scheduledStartTime) ? actualEndTime : scheduledStartTime,
                            scheduledEndTime)));
        } else {
            timeDiff.put(TimeStatus.OVERTIME,
                    timeDiff.get(TimeStatus.OVERTIME).plus(Duration.between(
                            scheduledEndTime.isAfter(actualStartTime) ? scheduledEndTime : actualStartTime,
                            actualEndTime)));
        }

        return timeDiff;
    }

    /*
     * Приведение long минут к формату HH:MM в виде строки
     */
    protected String minutesToTimeFormat(long minutes) {
        return String.format("%d:%02d", minutes/60, minutes%60);
    }

    public WorkTimeReport toWorkTimeReport(WorkTimeReportInfo info) {
        WorkTimeReport workTimeReport = new WorkTimeReport();

        workTimeReport.setDate(LocalDate.parse(info.getDate()));
        workTimeReport.setAbsenceReason(AbsenceReason.valueOf(info.getAbsenceReason()));

        TimePeriod workedTime = new TimePeriod();
        workedTime.setStartTime(LocalTime.parse(info.getStartTime()));
        workedTime.setEndTime(LocalTime.parse(info.getEndTime()));
        workTimeReport.setWorkedTime(workedTime);

        return workTimeReport;
    };
}
