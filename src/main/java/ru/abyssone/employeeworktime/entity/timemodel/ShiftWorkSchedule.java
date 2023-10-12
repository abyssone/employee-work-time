package ru.abyssone.employeeworktime.entity.timemodel;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class ShiftWorkSchedule extends WorkTimeModel {
    private LocalDate startWorkSchedule;
    private Integer workDaysNumber;
    private Integer daysOffNumber;
    private Integer workHours;

    public ShiftWorkSchedule() {
    }

    public ShiftWorkSchedule(LocalDate startWorkSchedule, Integer workDaysNumber, Integer daysOffNumber, Integer workHours) {
        this.startWorkSchedule = startWorkSchedule;
        this.workDaysNumber = workDaysNumber;
        this.daysOffNumber = daysOffNumber;
        this.workHours = workHours;
    }

    @Override
    public Integer getWorkHours(LocalDate date) {
        // Количество дней со дня начала рабочего графика

        long daysFromStart = Duration.between(
                LocalDateTime.of(startWorkSchedule, LocalTime.MIDNIGHT),
                LocalDateTime.of(date, LocalTime.MIDNIGHT)).toDays();

        return null;
    }

    @Override
    public Map<LocalDate, Integer> getWorkHours(LocalDate start, LocalDate end) {
        return null;
    }
}
