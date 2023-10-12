package ru.abyssone.employeeworktime.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IllegalDateValue extends IllegalArgumentException{

    public IllegalDateValue() {
        super();
    }

    public IllegalDateValue(String s) {
        super(s);
    }

    public IllegalDateValue(LocalDate ld) {
        super(ld.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}
