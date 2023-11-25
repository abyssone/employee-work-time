package ru.abyssone.employeeworktime.service.util.exception;

import ru.abyssone.employeeworktime.dto.report.WorkTimeReportInfo;

public class IllegalWorkTimeReportInfo extends IllegalEntityException{

    public IllegalWorkTimeReportInfo(String msg) {
        super(WorkTimeReportInfo.class.toString(), msg);
    }
}
