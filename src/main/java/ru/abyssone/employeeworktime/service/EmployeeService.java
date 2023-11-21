package ru.abyssone.employeeworktime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.dto.FullEmployeeInfo;
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
    private final EmployeeMapper employeeMapper;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    public List<GeneralEmployeeInfo> getAllGeneralEmployeeInfo() {
        List<Employee> all = employeeRepository.findAll();
        return all.stream().map(employeeMapper::toGeneralEmployeeInfo).toList();
    }

    public FullEmployeeInfo getFullEmployeeInfo(UUID employeeId) {
        Optional<Employee> employee = employeeRepository.findByIdFetchContract(employeeId);
        if (employee.isEmpty()) throw new NullPointerException(
                String.format("Employee with id:%d is not exist", employeeId));
        return employeeMapper.toFullEmployeeInfo(employee.get());
    }

    public Optional<GeneralEmployeeInfo> getGeneralEmployeeInfoById(UUID id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        GeneralEmployeeInfo employeeInfo = employeeMapper.toGeneralEmployeeInfo(employee.get());
        return Optional.of(employeeInfo);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public void save(GeneralEmployeeInfo employeeInfo) {
        Employee employee = employeeMapper.toEmployee(employeeInfo);
        employeeRepository.save(employee);
    };
}
