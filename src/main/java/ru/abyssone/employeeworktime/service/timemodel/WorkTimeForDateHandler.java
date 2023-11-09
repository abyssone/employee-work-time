package ru.abyssone.employeeworktime.service.timemodel;

import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.LocalDate;
import java.util.Map;

public interface WorkTimeForDateHandler {
    /**
     * Определяет метод для вычисления рабочего времени по дате и модели рабочего графика
     * @param model модель рабочего графика
     * @param date дата
     * @return рабочие часы
     */
    TimePeriod getWorkTimeForDate(WorkTimeModel model, LocalDate date);

    /**
     * Определяет метод для вычисления рабочего времени по диапазону дат и модели рабочего графика
     * @param model модель рабочего графика
     * @param start начальная дата диапазона
     * @param end конечная дата диапазона
     * @return мапа "дата: рабочие часы"
     */
    Map<LocalDate, TimePeriod> getWorkTimeForDatePeriod(WorkTimeModel model, LocalDate start, LocalDate end);
}
