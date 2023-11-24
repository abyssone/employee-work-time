package ru.abyssone.employeeworktime.service.util;

import org.springframework.stereotype.Component;
import ru.abyssone.employeeworktime.dto.WorkTimeReportInfo;
import ru.abyssone.employeeworktime.entity.AbsenceReason;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.service.util.exception.IllegalEmployeeException;
import ru.abyssone.employeeworktime.service.util.exception.IllegalWorkTimeReportInfo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

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
}
