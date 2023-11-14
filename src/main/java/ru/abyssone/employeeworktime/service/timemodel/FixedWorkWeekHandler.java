package ru.abyssone.employeeworktime.service.timemodel;

import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class FixedWorkWeekHandler implements WorkTimeForDateHandler {
    @Override
    public TimePeriod getWorkTimeForDate(WorkTimeModel workTimeModel, LocalDate date) {
        FixedWorkWeek fixedWorkWeek = (FixedWorkWeek) workTimeModel;
        return fixedWorkWeek.getWorkHours().get(date.getDayOfWeek());
    }

    @Override
    public Map<LocalDate, TimePeriod> getWorkTimeForDatePeriod(WorkTimeModel workTimeModel,
                                                               LocalDate start,
                                                               LocalDate end) {
        FixedWorkWeek workSchedule = (FixedWorkWeek) workTimeModel;
        LocalDate currentDate = start;
        Map<LocalDate, TimePeriod> result = new HashMap<>();

        // Количество дней между start и end датами
        long duration = DAYS.between(start, end);

        // Итерирование i количеству дней и инкрементирование currentDate
        for (long i = 0; i < duration; i++) {
            result.put(currentDate, workSchedule.getWorkHours().get(currentDate.getDayOfWeek()));
            currentDate = currentDate.plusDays(1);
        }

        return result;
    }
}
