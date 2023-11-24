package ru.abyssone.employeeworktime.service.util.exception;

import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

public class IllegalTimePeriod extends IllegalEntityException{

    public IllegalTimePeriod(String msg) {
        super(TimePeriod.class.toString(), msg);
    }
}
