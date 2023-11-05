package ru.abyssone.employeeworktime.entity.timemodel;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abyssone.employeeworktime.exception.IllegalDateValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShiftWorkSchedule extends WorkTimeModel {
    private LocalDate startWorkSchedule;
    private Integer workDaysNumber;
    private Integer daysOffNumber;
    private Integer workHours;

    public ShiftWorkSchedule(LocalDate startWorkSchedule, Integer workDaysNumber, Integer daysOffNumber, Integer workHours) {
        this.startWorkSchedule = startWorkSchedule;
        this.workDaysNumber = workDaysNumber;
        this.daysOffNumber = daysOffNumber;
        this.workHours = workHours;
    }

    @Override
    public Integer getWorkHours(LocalDate date) {
        // Количество дней со дня начала рабочего графика

        long daysFromStartSchedule = DAYS.between(startWorkSchedule, date);

        // Проверка корректности даты. Аргумент date не может быть меньше(раньше) даты начала графика.
        if (daysFromStartSchedule < 0) throw new IllegalDateValue(
                String.format("%s earlie than date of start work schedule(%s)",
                        date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        startWorkSchedule.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));

        // Вычисление количества дней с начала последней итерации по графику (полный цикл: раб. дни + выходные)
        Integer daysFromStartOfWork = (int) daysFromStartSchedule % (workDaysNumber + daysOffNumber);
        return daysFromStartOfWork < workDaysNumber ? workHours : 0;
    }

    /**
     * Вычисление количества дней после начала работы ("рабочей недели"). После этого параллельная
     * итерация по датам заданного промежутка и по дням сменного графика.
     * @param start
     * @param end
     * @return
     */
    //TODO: пересмотреть и оптимизировать
    @Override
    public Map<LocalDate, Integer> getWorkHours(LocalDate start, LocalDate end) {
        LocalDate currentDate = start;
        Map<LocalDate, Integer> result = new HashMap<>();
        int scheduleDaysNumb = workDaysNumber + daysOffNumber;
        long daysFromStartSchedule = DAYS.between(startWorkSchedule, start);
        int currentScheduleDay = (int) daysFromStartSchedule % scheduleDaysNumb;
        long duration = DAYS.between(start, end);

        for (long i = 0; i < duration; i++) {
            if (currentScheduleDay >= scheduleDaysNumb) currentScheduleDay = 1;
            result.put(currentDate, currentScheduleDay < workDaysNumber ? workHours : 0);
            currentScheduleDay++;
            currentDate = currentDate.plusDays(1);
        }
        Integer daysFromStartOfWork = (int) daysFromStartSchedule % (workDaysNumber + daysOffNumber);

        return result;
    }
}
