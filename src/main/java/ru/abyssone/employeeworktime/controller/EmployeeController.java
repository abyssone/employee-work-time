package ru.abyssone.employeeworktime.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/")
    public List<Employee> getAll() {
        return employeeService.findAll();
    }

    @PostMapping("/")
    public void addEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
    }
}
