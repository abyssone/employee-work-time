package ru.abyssone.employeeworktime.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.abyssone.employeeworktime.dto.ExceptionalDayInfo;
import ru.abyssone.employeeworktime.service.EmployeeService;
import ru.abyssone.employeeworktime.service.ExceptionalDayService;

@Controller
@RequiredArgsConstructor
public class ExceptionalDayController {

    private final EmployeeService employeeService;
    private final ExceptionalDayService exceptionalDayService;

    @GetMapping("/exceptionalday/create")
    public String getExceptionalDayCreating(Model model) {
        model.addAttribute("employees", employeeService.getGeneralEmployeeWithContractInfo());
        return "exceptional-day-creating";
    }

    @PostMapping("/exceptionalday/create")
    public String createExceptionalDay(@ModelAttribute ExceptionalDayInfo exceptionalDayInfo) {
        exceptionalDayService.save(exceptionalDayInfo);
        return "redirect:/exceptionalday/create";
    }
}
