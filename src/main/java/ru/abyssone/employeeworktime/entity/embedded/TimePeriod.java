package ru.abyssone.employeeworktime.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Embeddable
@Getter
@Setter
public class TimePeriod {
    private LocalTime startTime;
    private LocalTime endTime;
}
