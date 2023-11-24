package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.abyssone.employeeworktime.dto.employee.FullEmployeeInfo;
import ru.abyssone.employeeworktime.dto.employee.GeneralEmployeeInfo;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.Employee;

import java.util.Optional;

@Mapper
public abstract class EmployeeMapper {

    @Autowired
    protected ContractMapper contractMapper;

    @Autowired
    protected ScheduleMapper scheduleMapper;

    public GeneralEmployeeInfo toGeneralEmployeeInfo(Employee employee) {
        GeneralEmployeeInfo empInfo = new GeneralEmployeeInfo();
        empInfo.setId(employee.getId());
        empInfo.setName(employee.getName());

        empInfo.setSex(employee.getSex().name());

        empInfo.setAddress(employee.getAddress());
        empInfo.setBirthDate(employee.getBirthDate());

        Optional<Contract> contract = employee.getContract();

        if(contract.isEmpty()) {
            empInfo.setContract(Optional.empty());
        } else {
            empInfo.setContract(Optional.of(contractMapper.toGeneralContractInfo(contract.get())));
        }

        return empInfo;
    };

    public Employee toEmployee(GeneralEmployeeInfo employeeInfo) {
        Employee employee = new Employee();

        if (employeeInfo.getId() != null) employee.setId(employeeInfo.getId());
        employee.setName(employeeInfo.getName());
        employee.setSex(Employee.Sex.valueOf(employeeInfo.getSex()));
        employee.setAddress(employeeInfo.getAddress());
        employee.setBirthDate(employeeInfo.getBirthDate());

        return employee;
    }

    public FullEmployeeInfo toFullEmployeeInfo(Employee employee) {
        FullEmployeeInfo empInfo = new FullEmployeeInfo();

        empInfo.setId(employee.getId());
        empInfo.setName(employee.getName());
        if (employee.getContract().isEmpty()) {
            empInfo.setSchedule(Optional.empty());
            empInfo.setContract(Optional.empty());
        } else {
            empInfo.setSchedule(Optional.of(
                    scheduleMapper.toScheduleDescription(employee.getContract().get().getWorkTimeModel())));
            empInfo.setContract(Optional.of(
                    contractMapper.toGeneralContractInfo(employee.getContract().get())));
        }

        return empInfo;
    }
}
