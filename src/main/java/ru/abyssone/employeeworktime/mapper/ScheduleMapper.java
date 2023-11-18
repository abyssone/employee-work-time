package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.abyssone.employeeworktime.dto.ScheduleDescription;
import ru.abyssone.employeeworktime.dto.ScheduleInfo;
import ru.abyssone.employeeworktime.dto.ScheduleType;
import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;
import ru.abyssone.employeeworktime.entity.timemodel.ShiftWorkSchedule;
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
}
