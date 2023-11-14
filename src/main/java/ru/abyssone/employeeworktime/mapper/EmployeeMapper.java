package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.abyssone.employeeworktime.dto.GeneralEmployeeInfo;
import ru.abyssone.employeeworktime.entity.Employee;

@Mapper(uses = {ContractMapper.class})
public interface EmployeeMapper {

    @Mapping(source = "employee.contracts", target = "contracts")
    GeneralEmployeeInfo toGeneralEmployeeInfo(Employee employee);
}
