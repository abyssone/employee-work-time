package ru.abyssone.employeeworktime.repository.custom;

import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.service.EmployeeService;

import java.util.List;

public interface CustomEmployeeRepository {

    List<Employee> findAllFilteredAndSorted(String search, EmployeeService.SortField sort);
}
