package ru.abyssone.employeeworktime.entity.timemodel;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.LocalDate;

/**
 * Реализация "сменного графика работы" абстрактной сущности "график работы" (WorkTimeModel).
 * Содержит необходимые данные для вычисления рабочих и выходных дней при сменном графике
 * работы.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShiftWorkSchedule extends WorkTimeModel {

    // Дата начала графика. Начальная точка отсчета
    private LocalDate startWorkSchedule;

    // Количество рабочих дней
    private Integer workDaysNumber;

    // Количество выходных дней
    private Integer daysOffNumber;

    // Время начала и конца рабочего времени
    @Embedded
    private TimePeriod workHours;
}
