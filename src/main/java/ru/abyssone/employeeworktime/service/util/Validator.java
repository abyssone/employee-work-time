package ru.abyssone.employeeworktime.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.abyssone.employeeworktime.dto.ExceptionalDayInfo;
import ru.abyssone.employeeworktime.dto.report.WorkTimeReportInfo;
import ru.abyssone.employeeworktime.entity.AbsenceReason;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.service.util.exception.IllegalEmployeeException;
import ru.abyssone.employeeworktime.service.util.exception.IllegalExceptionalDayInfo;
import ru.abyssone.employeeworktime.service.util.exception.IllegalTimePeriod;
import ru.abyssone.employeeworktime.service.util.exception.IllegalWorkTimeReportInfo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Slf4j
@Component
public class Validator {

    public void check(Employee employee) throws IllegalEmployeeException {

        if (employee == null) throw new IllegalEmployeeException("Employee is null");
        if (employee.getId() == null) throw new IllegalEmployeeException("Employee.id is null");
        if (employee.getName().isEmpty()) throw new IllegalEmployeeException(String.format(
                "Employee with id:%s doesn't contains name", employee.getId().toString()
        ));
        if (employee.getBirthDate() == null) throw new IllegalEmployeeException(String.format(
                "Employee with id:%s doesn't contains birth date", employee.getId().toString()
        ));
        if (employee.getBirthDate().isAfter(LocalDate.now().plusYears(18)))
            throw new IllegalEmployeeException(String.format("Employee with id:%s is younger than 18 years old." +
                    "This violates art.63 of the LC RF", employee.getId().toString()));
        if (employee.getSex() == null) throw new IllegalEmployeeException(String.format(
                "Employee with id:%s doesn't contains sex", employee.getId().toString()
        ));
    }

    public void check(WorkTimeReportInfo reportInfo) throws IllegalWorkTimeReportInfo {
        if (reportInfo == null) throw new IllegalWorkTimeReportInfo("WorkTimeReportInfo is null");
        try {
            LocalDate.parse(reportInfo.getDate());
        } catch (DateTimeParseException exception) {
            throw new IllegalWorkTimeReportInfo(String.format("WorkTimeReportInfo date is incorrect %s",
                    reportInfo.getDate()));
        }
        try {
            LocalTime.parse(reportInfo.getStartTime());
        } catch (DateTimeParseException exception) {
            throw new IllegalWorkTimeReportInfo(String.format("WorkTimeReportInfo start time is incorrect %s",
                    reportInfo.getStartTime()));
        }
        try {
            LocalTime.parse(reportInfo.getEndTime());
        } catch (DateTimeParseException exception) {
            throw new IllegalWorkTimeReportInfo(String.format("WorkTimeReportInfo end time is incorrect %s",
                    reportInfo.getEndTime()));
        }
        try {
            AbsenceReason.valueOf(reportInfo.getAbsenceReason());
        } catch (IllegalArgumentException exception) {
            throw new IllegalWorkTimeReportInfo(String.format("WorkTimeReportInfo absence reason is incorrect %s",
                    reportInfo.getAbsenceReason()));
        }
    }

    public void checkStringTimePeriod(String start, String end) throws IllegalTimePeriod {
        if (start != null) {
            if (end == null) throw new IllegalTimePeriod("end time is null when start isn't null");
            try {
                LocalTime.parse(start);
            } catch (DateTimeParseException e) {
                log.error(e.getMessage());
                throw new IllegalTimePeriod("error when parsing start time: " + start);
            }
        }
        if (end != null) {
            if (start == null) throw new IllegalTimePeriod("start time is null when end isn't null");
            try {
                LocalTime.parse(end);
            } catch (DateTimeParseException e) {
                log.error(e.getMessage());
                throw new IllegalTimePeriod("error when parsing end time: " + end);
            }
        }
        if (end != null && start != null) {
            if (LocalTime.parse(end).isAfter(LocalTime.parse(start))) {
                String msg = String.format("end time (%s) cannot be earlier than start time (%s)", end, start);
                log.error(msg);
                throw new IllegalTimePeriod(msg);
            }
        }
    }

    public void check(ExceptionalDayInfo exceptionalDayInfo) throws IllegalExceptionalDayInfo {
        if (exceptionalDayInfo == null) throw new IllegalExceptionalDayInfo("is null");
        try {
            this.checkStringTimePeriod(exceptionalDayInfo.getStartTime(), exceptionalDayInfo.getEndTime());
        } catch (IllegalTimePeriod e) {
            log.error(e.getMessage());
            throw new IllegalExceptionalDayInfo("incorrect time period");
        }
        if (exceptionalDayInfo.getDate() == null) throw new IllegalExceptionalDayInfo("date is null");
        try {
            LocalDate.parse(exceptionalDayInfo.getDate());
        } catch (DateTimeParseException e) {
            log.error(e.getMessage());
            throw new IllegalExceptionalDayInfo("incorrect date: " + exceptionalDayInfo.getDate());
        }
        if (exceptionalDayInfo.getContracts().isEmpty()) {
            throw new IllegalExceptionalDayInfo("contracts is null");
        }
    }
}
