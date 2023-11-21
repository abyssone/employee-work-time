package ru.abyssone.employeeworktime.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.abyssone.employeeworktime.dto.FullEmployeeInfo;
import ru.abyssone.employeeworktime.dto.GeneralEmployeeInfo;
import ru.abyssone.employeeworktime.service.EmployeeService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/")
    public String greetings(Model model) {
        model.addAttribute("employees", employeeService.getAllGeneralEmployeeInfo());
        return "all-employees-info";
    }

    @GetMapping("/employee/create")
    public String getEmployeeCreating() {
        return "employee-creating";
    }

    @PostMapping("/employee/create")
    public String createEmployee(@ModelAttribute GeneralEmployeeInfo employeeInfo) {
        employeeService.save(employeeInfo);
        return "redirect:/";
    }

    @GetMapping("employee/{id}")
    public String getEmployee(@PathVariable("id") UUID id, Model model) {
        FullEmployeeInfo fullEmployeeInfo = employeeService.getFullEmployeeInfo(id);
        model.addAttribute("employee", fullEmployeeInfo);
        return "employee-info";
    }
}
