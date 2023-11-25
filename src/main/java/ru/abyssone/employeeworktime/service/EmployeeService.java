package ru.abyssone.employeeworktime.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.dto.employee.FullEmployeeInfo;
import ru.abyssone.employeeworktime.dto.employee.GeneralEmployeeInfo;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.mapper.EmployeeMapper;
import ru.abyssone.employeeworktime.repository.EmployeeRepository;
import ru.abyssone.employeeworktime.service.util.Validator;
import ru.abyssone.employeeworktime.service.util.exception.IllegalEmployeeException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final Validator validator;

    public List<GeneralEmployeeInfo> getAllGeneralEmployeeInfo() {
        List<Employee> all = employeeRepository.findAll();
        return all.stream().map(employeeMapper::toGeneralEmployeeInfo).toList();
    }

    public List<GeneralEmployeeInfo> getGeneralEmployeeWithContractInfo() {
        List<Employee> all = employeeRepository.findAllWithContract();
        return all.stream().map(employeeMapper::toGeneralEmployeeInfo).toList();
    }

    public FullEmployeeInfo getFullEmployeeInfo(UUID employeeId) {
        Optional<Employee> employee = employeeRepository.findByIdFetchContract(employeeId);
        if (employee.isEmpty()) throw new NullPointerException(
                String.format("Employee with id:%d is not exist", employeeId));
        return employeeMapper.toFullEmployeeInfo(employee.get());
    }

    public GeneralEmployeeInfo getGeneralEmployeeInfoById(UUID id) throws NullPointerException{
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            String msg = String.format("Employee with id: %s not found", id);
            log.error(msg);
            throw new NullPointerException(msg);
        }

        GeneralEmployeeInfo employeeInfo = employeeMapper.toGeneralEmployeeInfo(employee.get());
        return employeeInfo;
    }

    public void save(GeneralEmployeeInfo employeeInfo) throws IllegalEmployeeException{
        Employee employee = employeeMapper.toEmployee(employeeInfo);

        // Если entity не прошло валидацию, то оно не сохраняется в бд и передается дальше в контроллер
        try {
            validator.check(employee);
            employeeRepository.save(employee);
        } catch (IllegalEmployeeException ex) {
            log.error(ex.getMessage());
            throw new IllegalEmployeeException(ex.getMessage());
        }
    };

    public void update(GeneralEmployeeInfo employeeInfo) {
        Employee employee = employeeMapper.toEmployee(employeeInfo);

        try {
            validator.check(employee);
        } catch (IllegalEmployeeException ex) {
            log.error(ex.getMessage());
            throw ex;
        }

        employeeRepository.save(employee);
    }
}
