package ru.abyssone.employeeworktime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.dto.schedule.ScheduleDescription;
import ru.abyssone.employeeworktime.dto.schedule.ScheduleInfo;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;
import ru.abyssone.employeeworktime.mapper.ScheduleMapper;
import ru.abyssone.employeeworktime.repository.WorkTimeModelObject;
import ru.abyssone.employeeworktime.repository.WorkTimeModelRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final WorkTimeModelRepository workTimeModelRepository;
    private final ScheduleMapper scheduleMapper;

    public void save(ScheduleInfo scheduleInfo) {
        WorkTimeModel model;

        switch (scheduleInfo.getType()) {
            case FIXED_WORK_WEEK -> {
                Set<DayOfWeek> dayOfWeeks = scheduleInfo.getWorkWeekHours().keySet();

                for (DayOfWeek dayOfWeek : dayOfWeeks) {
                    TimePeriod tp = scheduleInfo.getWorkWeekHours().get(dayOfWeek);

                    if (tp == null || tp.getEndTime() == null || tp.getStartTime() == null) {
                        scheduleInfo.getWorkWeekHours().put(dayOfWeek, null);
                    }
                }
                model = scheduleMapper.toFixedWorkWeek(scheduleInfo);
            }
            case SHIFT_WORK_SCHEDULE -> model = scheduleMapper.toShiftWorkSchedule(scheduleInfo);
            default -> throw new IllegalArgumentException(String.format("Неверный тип графика: %s",
                                                                        scheduleInfo.getType()));
        }
        workTimeModelRepository.save(model);
    }

    public List<ScheduleDescription> findAllAsScheduleDescription() {
        List<WorkTimeModelObject> allAsIdAndName = workTimeModelRepository.findAllAsIdAndName();
        return allAsIdAndName.stream().map(scheduleMapper::toScheduleDescription).toList();
    }
}
