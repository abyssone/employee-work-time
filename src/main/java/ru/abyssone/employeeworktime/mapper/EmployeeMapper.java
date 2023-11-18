package ru.abyssone.employeeworktime.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.abyssone.employeeworktime.dto.GeneralEmployeeInfo;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.Employee;

import java.util.Optional;

@Mapper
public abstract class EmployeeMapper {

    @Autowired
    protected ContractMapper contractMapper;

    public GeneralEmployeeInfo toGeneralEmployeeInfo(Employee employee) {
        GeneralEmployeeInfo empInfo = new GeneralEmployeeInfo();
        empInfo.setId(employee.getId());
        empInfo.setName(employee.getName());

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

        return employee;
    }
}
