package ru.abyssone.employeeworktime.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.abyssone.employeeworktime.dto.ExceptionalDayInfo;
import ru.abyssone.employeeworktime.service.EmployeeService;
import ru.abyssone.employeeworktime.service.ExceptionalDayService;
import ru.abyssone.employeeworktime.service.util.exception.IllegalExceptionalDayInfo;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ExceptionalDayController {

    private final EmployeeService employeeService;
    private final ExceptionalDayService exceptionalDayService;

    @GetMapping("/exceptionalday")
    public String getExceptionalDays(Model model) {
        model.addAttribute("exceptionalDays", exceptionalDayService.getAllExceptionalDayInfo());
        return "all-exceptional-days-info";
    }

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

    @GetMapping("/exceptionalday/{id}/edit")
    public String getExceptionalDayEditing(@PathVariable("id") Long id,
                                           Model model) {
        model.addAttribute("exceptionalDay", exceptionalDayService.getExceptionalDayInfoWithContracts(id));
        model.addAttribute("employees", employeeService.getGeneralEmployeeWithContractInfo());

        return "exceptional-day-editing";
    }

    @PostMapping("/exceptionalday/{id}/edit")
    public String editExceptionalDay(@PathVariable("id") Long id,
                                     @ModelAttribute ExceptionalDayInfo exceptionalDayInfo) {

        exceptionalDayInfo.setId(id);
        exceptionalDayService.save(exceptionalDayInfo);
        return "redirect:/exceptionalday";
    }
}
