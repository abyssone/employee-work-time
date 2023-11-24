package ru.abyssone.employeeworktime.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkTimeReportInfo {
    private String date;
    private String startTime;
    private String endTime;
    private String absenceReason;
}
