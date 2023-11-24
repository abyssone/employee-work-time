package ru.abyssone.employeeworktime.service.util.exception;

import ru.abyssone.employeeworktime.dto.ExceptionalDayInfo;

public class IllegalExceptionalDayInfo extends IllegalEntityException{

    public IllegalExceptionalDayInfo(String msg) {
        super(ExceptionalDayInfo.class.toString(), msg);
    }
}
