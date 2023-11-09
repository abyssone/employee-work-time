package ru.abyssone.employeeworktime.entity.timemodel;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShiftWorkSchedule extends WorkTimeModel {
    private LocalDate startWorkSchedule;
    private Integer workDaysNumber;
    private Integer daysOffNumber;

    @Embedded
    private TimePeriod workHours;
}
