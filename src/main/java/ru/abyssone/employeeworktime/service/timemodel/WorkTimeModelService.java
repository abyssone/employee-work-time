package ru.abyssone.employeeworktime.service.timemodel;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;
import ru.abyssone.employeeworktime.entity.timemodel.ShiftWorkSchedule;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkTimeModelService {

    private final FixedWorkWeekHandler fixedWorkWeekHandler;
    private final ShiftWorkScheduleHandler shiftWorkScheduleHandler;

    private Map<Class, WorkTimeForDateHandler> handlers = new HashMap<>();

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

    @PostConstruct
    private void init() {
        this.handlers.put(FixedWorkWeek.class, fixedWorkWeekHandler);
        this.handlers.put(ShiftWorkSchedule.class, shiftWorkScheduleHandler);
    }
}
