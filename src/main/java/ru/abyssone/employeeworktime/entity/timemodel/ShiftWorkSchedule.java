package ru.abyssone.employeeworktime.entity.timemodel;

import ru.abyssone.employeeworktime.exception.IllegalDateValue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

        // Проверка корректности даты. Аргумент date не может быть меньше(раньше) даты начала графика.
        if (daysFromStart < 0) throw new IllegalDateValue(
                String.format("%s earlie than date of start work schedule(%s)",
                        date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        startWorkSchedule.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));

        // Вычисление количества дней с начала последней итерации по графику (полный цикл: раб. дни + выходные)
        Integer daysFromStartOfWork = (int) daysFromStart % (workDaysNumber + daysOffNumber);
        return daysFromStartOfWork < workDaysNumber ? workHours : 0;
    }

    @Override
    public Map<LocalDate, Integer> getWorkHours(LocalDate start, LocalDate end) {
        return null;
    }
}
