package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.abyssone.employeeworktime.dto.schedule.ScheduleDescription;
import ru.abyssone.employeeworktime.dto.schedule.ScheduleInfo;
import ru.abyssone.employeeworktime.dto.schedule.ScheduleType;
import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;
import ru.abyssone.employeeworktime.entity.timemodel.ShiftWorkSchedule;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;
import ru.abyssone.employeeworktime.repository.WorkTimeModelObject;

@Mapper
public interface ScheduleMapper {

    @Mapping(source = "scheduleInfo.workWeekHours", target = "workHours")
    @Mapping(target = "id", ignore = true)
    FixedWorkWeek toFixedWorkWeek(ScheduleInfo scheduleInfo);

    @Mapping(target = "id", ignore = true)
    ShiftWorkSchedule toShiftWorkSchedule(ScheduleInfo scheduleInfo);

    default ScheduleDescription toScheduleDescription(WorkTimeModelObject object) {
        ScheduleDescription scheduleDesc = new ScheduleDescription();
        scheduleDesc.setId(object.getId());
        scheduleDesc.setTitle(object.getTitle());
        scheduleDesc.setType(ScheduleType.valueOf(object.getClazz()));
        return scheduleDesc;
    }

    default ScheduleDescription toScheduleDescription(WorkTimeModel model) {
        ScheduleDescription scheduleDesc = new ScheduleDescription();
        scheduleDesc.setId(model.getId());
        scheduleDesc.setTitle(model.getTitle());

        switch (model.getClass().getSimpleName()) {
            case "ShiftWorkSchedule" -> scheduleDesc.setType(ScheduleType.SHIFT_WORK_SCHEDULE);
            case "FixedWorkWeek" -> scheduleDesc.setType(ScheduleType.FIXED_WORK_WEEK);
            default -> throw new IllegalArgumentException("Incorrect WorkTimeModel type: " + model.getClass().getName());
        }

        return scheduleDesc;
    }
}
