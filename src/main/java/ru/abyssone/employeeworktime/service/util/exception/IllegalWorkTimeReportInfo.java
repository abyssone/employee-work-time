package ru.abyssone.employeeworktime.service.util.exception;

public class IllegalWorkTimeReportInfo extends IllegalArgumentException{

    public IllegalWorkTimeReportInfo() {
        super();
    }

    public IllegalWorkTimeReportInfo(String s) {
        super(s);
    }
}
