package ru.abyssone.employeeworktime.service.timemodel;

import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.exception.IllegalDateValue;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.entity.timemodel.ShiftWorkSchedule;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ShiftWorkScheduleHandler implements WorkTimeForDateHandler {
    @Override
    public TimePeriod getWorkTimeForDate(WorkTimeModel workTimeModel, LocalDate date) {
        ShiftWorkSchedule workSchedule = (ShiftWorkSchedule) workTimeModel;

        // Количество дней со дня начала работы

        long daysFromStartSchedule = DAYS.between(workSchedule.getStartWorkSchedule(), date);

        // Проверка корректности даты. Аргумент date не может быть меньше(раньше) даты начала графика.
        if (daysFromStartSchedule < 0) throw new IllegalDateValue(
                String.format("%s earlie than date of start work schedule(%s)",
                        date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        workSchedule.getStartWorkSchedule().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));

        // Вычисление количества дней с начала последней итерации по графику (полный цикл: раб. дни + выходные)
        Integer workDaysNumber = workSchedule.getWorkDaysNumber();
        Integer daysOffNumber = workSchedule.getDaysOffNumber();

        Integer daysFromStartOfWork = (int) daysFromStartSchedule % (workDaysNumber + daysOffNumber);

        return daysFromStartOfWork < workDaysNumber ? workSchedule.getWorkHours() : null;
    }

    /**
     * Вычисление количества дней после начала работы ("рабочей недели"). После этого параллельная
     * итерация по датам заданного промежутка и по дням сменного графика.
     * @param model модель рабочего графика
     * @param start начальная дата диапазона
     * @param end конечная дата диапазона
     * @return мапа "дата: рабочие часы" в диапазоне start — (end - 1)
     */
    @Override
    public Map<LocalDate, TimePeriod> getWorkTimeForDatePeriod(WorkTimeModel model,
                                                               LocalDate start,
                                                               LocalDate end) {
        ShiftWorkSchedule workSchedule = (ShiftWorkSchedule) model;
        LocalDate currentDate = start;
        Map<LocalDate, TimePeriod> result = new HashMap<>();
        Integer workDaysNumber = workSchedule.getWorkDaysNumber();
        TimePeriod workHours = workSchedule.getWorkHours();

        // Количество дней в графике (рабочие дни + выходные)
        int scheduleDaysNumb = workDaysNumber + workSchedule.getDaysOffNumber();

        // Количество дней между началом графика (первым рабочим днем)
        // и начальным днем запрашиваемого диапазона
        long daysFromStartSchedule = DAYS.between(workSchedule.getStartWorkSchedule(), start);

        // Номер дня в текущей итерации по графику
        int currentScheduleDay = (int) daysFromStartSchedule % scheduleDaysNumb;

        // Количество дней между start и end датами
        long duration = DAYS.between(start, end);

        for (long i = 0; i < duration; i++) {

            // Переход на новую итерацию по графику происходит с "0" дня
            if (currentScheduleDay >= scheduleDaysNumb) currentScheduleDay = 0;

            result.put(currentDate, currentScheduleDay < workDaysNumber ? workHours : null);
            currentScheduleDay++;
            currentDate = currentDate.plusDays(1);
        }

        return result;
    }
}
