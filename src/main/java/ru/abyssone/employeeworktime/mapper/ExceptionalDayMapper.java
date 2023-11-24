package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.abyssone.employeeworktime.dto.ExceptionalDayInfo;
import ru.abyssone.employeeworktime.entity.ExceptionalDay;

@Mapper
public interface ExceptionalDayMapper {

    @Mapping(source = "exceptionalDayInfo.contracts", target = "contracts", ignore = true)
    @Mapping(source = "exceptionalDayInfo.startTime", target = "workTime.startTime")
    @Mapping(source = "exceptionalDayInfo.endTime", target = "workTime.endTime")
    ExceptionalDay toExceptionalDay(ExceptionalDayInfo exceptionalDayInfo);
}
