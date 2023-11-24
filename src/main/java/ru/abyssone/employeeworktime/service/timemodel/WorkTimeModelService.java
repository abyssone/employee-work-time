package ru.abyssone.employeeworktime.service.timemodel;

import org.springframework.stereotype.Component;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;
import ru.abyssone.employeeworktime.entity.timemodel.ShiftWorkSchedule;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class WorkTimeModelService {

    private Map<Class, WorkTimeForDateHandler> handlers = new HashMap<>();

    public WorkTimeModelService(FixedWorkWeekHandler fixedWorkWeekHandler,
                                ShiftWorkScheduleHandler shiftWorkScheduleHandler) {

        this.handlers.put(FixedWorkWeek.class, fixedWorkWeekHandler);
        this.handlers.put(ShiftWorkSchedule.class, shiftWorkScheduleHandler);
    }

    public TimePeriod getWorkHoursForDate(WorkTimeModel model, LocalDate date) {
        return handlers.get(model.getClass()).getWorkTimeForDate(model, date);
    }

    /**
     * Рабочие часы за период
     * @param model рабочий график
     * @param start дата начала периода (включает дату)
     * @param end дата окончания периода (не включает дату)
     * @return мапа "дата: рабочие часы" в диапазоне start — (end - 1)
     */
    public Map<LocalDate, TimePeriod> getWorkHoursForDatePeriod(WorkTimeModel model,
                                                                LocalDate start,
                                                                LocalDate end) {
        return handlers.get(model.getClass()).getWorkTimeForDatePeriod(model, start, end);
    }
}
