package ru.abyssone.employeeworktime.entity.embedded;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class TimePeriod {
    private LocalTime start;
    private LocalTime end;
}
