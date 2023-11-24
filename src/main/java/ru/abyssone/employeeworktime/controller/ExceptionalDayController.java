package ru.abyssone.employeeworktime.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.abyssone.employeeworktime.dto.ExceptionalDayInfo;
import ru.abyssone.employeeworktime.service.EmployeeService;
import ru.abyssone.employeeworktime.service.ExceptionalDayService;
import ru.abyssone.employeeworktime.service.util.exception.IllegalEntityException;
import ru.abyssone.employeeworktime.service.util.exception.IllegalExceptionalDayInfo;
import ru.abyssone.employeeworktime.service.util.exception.IllegalWorkTimeReportInfo;

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

    @ExceptionHandler(value = {IllegalExceptionalDayInfo.class})
    public ModelAndView handleException(HttpServletRequest req, IllegalExceptionalDayInfo exception) {
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("exception", exception.getClass());
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", exception.getMessage());
        return mav;
    }
}
