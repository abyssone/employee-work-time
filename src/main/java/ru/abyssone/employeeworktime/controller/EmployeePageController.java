package ru.abyssone.employeeworktime.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.service.EmployeeService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class EmployeePageController {
    private final EmployeeService employeeService;
    @GetMapping("/page")
    public String greetings(Model model) {
        model.addAttribute("employees", employeeService.getAllGeneralEmployeeInfo());
        return "greatings-page";
    }

    @GetMapping("/employee/create")
    public String getEmployeeCreating() {
        return "employee-creating";
    }

    @PostMapping("/employee/create")
    public String createEmployee(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/page";
    }

    @GetMapping("employee/{id}")
    public String getEmployee(@PathVariable("id") UUID id, Model model) {
        Optional<Employee> employee = employeeService.findById(id);
        model.addAttribute("employee", employee.get());
        return "employee-info";
    }
}
