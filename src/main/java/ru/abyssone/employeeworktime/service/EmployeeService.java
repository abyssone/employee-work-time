package ru.abyssone.employeeworktime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.dto.GeneralEmployeeInfo;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.mapper.EmployeeMapper;
import ru.abyssone.employeeworktime.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper mapper;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);

    }

    public List<GeneralEmployeeInfo> getAllGeneralEmployeeInfo() {
        List<Employee> all = employeeRepository.findAll();
        return all.stream().map(mapper::toGeneralEmployeeInfo).toList();
    }

    public Optional<GeneralEmployeeInfo> getGeneralEmployeeInfoById(UUID id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        GeneralEmployeeInfo employeeInfo = mapper.toGeneralEmployeeInfo(employee.get());
        return Optional.of(employeeInfo);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }
}
