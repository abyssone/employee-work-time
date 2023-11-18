package ru.abyssone.employeeworktime.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.dto.ScheduleDescription;
import ru.abyssone.employeeworktime.dto.ScheduleInfo;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;
import ru.abyssone.employeeworktime.mapper.ScheduleMapper;
import ru.abyssone.employeeworktime.repository.WorkTimeModelObject;
import ru.abyssone.employeeworktime.repository.WorkTimeModelRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final WorkTimeModelRepository workTimeModelRepository;
    private final ScheduleMapper scheduleMapper;

    public void save(ScheduleInfo scheduleInfo) {
        WorkTimeModel model;

        switch (scheduleInfo.getType()) {
            case FIXED_WORK_WEEK -> model = scheduleMapper.toFixedWorkWeek(scheduleInfo);
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
