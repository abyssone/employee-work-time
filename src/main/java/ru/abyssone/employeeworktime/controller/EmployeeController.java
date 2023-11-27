package ru.abyssone.employeeworktime.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.abyssone.employeeworktime.dto.employee.FullEmployeeInfo;
import ru.abyssone.employeeworktime.dto.employee.GeneralEmployeeInfo;
import ru.abyssone.employeeworktime.service.EmployeeService;
import ru.abyssone.employeeworktime.service.util.exception.IllegalEmployeeException;
import ru.abyssone.employeeworktime.service.util.exception.IllegalExceptionalDayInfo;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/")
    public String greetings(Model model) {
        model.addAttribute("employees", employeeService.getAllGeneralEmployeeInfo());
        return "employee/all-employees-info";
    }

    @GetMapping("/employee/create")
    public String getEmployeeCreating() {
        return "employee/employee-creating";
    }

    @PostMapping("/employee/create")
    public String createEmployee(@ModelAttribute GeneralEmployeeInfo employeeInfo) {
        try {
            employeeService.save(employeeInfo);
        } catch (IllegalEmployeeException exception) {
            return "redirect:/employee/create";
        }
        return "redirect:/";
    }

    @GetMapping("employee/{id}")
    public String getEmployee(@PathVariable("id") UUID id, Model model) {
        FullEmployeeInfo fullEmployeeInfo = employeeService.getFullEmployeeInfo(id);
        model.addAttribute("employee", fullEmployeeInfo);
        return "employee/employee-info";
    }

    @GetMapping("employee/{id}/edit")
    public String getEmployeeEditing(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("employee", employeeService.getGeneralEmployeeInfoById(id));
        return "employee/employee-editing";
    }

    @PostMapping("employee/{id}/edit")
    public String getEmployeeEditing(@PathVariable("id") UUID id,
                                     @ModelAttribute GeneralEmployeeInfo employeeInfo) {
        employeeService.update(employeeInfo);
        return "redirect:/employee/{id}";
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ModelAndView handleException(HttpServletRequest req, NullPointerException exception) {
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("exception", exception.getClass());
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", exception.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {IllegalEmployeeException.class})
    public ModelAndView handleException(HttpServletRequest req, IllegalEmployeeException exception) {
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("exception", exception.getClass());
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", exception.getMessage());
        return mav;
    }
}
