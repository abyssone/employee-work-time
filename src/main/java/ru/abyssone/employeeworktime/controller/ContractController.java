package ru.abyssone.employeeworktime.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.abyssone.employeeworktime.dto.contract.FullContractInfo;
import ru.abyssone.employeeworktime.dto.report.WorkTimeReportInfo;
import ru.abyssone.employeeworktime.service.ContractService;
import ru.abyssone.employeeworktime.service.EmployeeService;
import ru.abyssone.employeeworktime.service.ReportService;
import ru.abyssone.employeeworktime.service.ScheduleService;
import ru.abyssone.employeeworktime.service.util.exception.IllegalWorkTimeReportInfo;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ContractController {

    private final EmployeeService employeeService;
    private final ScheduleService scheduleService;
    private final ContractService contractService;
    private final ReportService reportService;

    @GetMapping("/contract/create")
    public String getContractCreating(@RequestParam(value = "employee_id", required = false) UUID employee_id, Model model) {
        model.addAttribute("employees", employeeService.getAllGeneralEmployeeInfo());
        model.addAttribute("schedules", scheduleService.findAllAsScheduleDescription());
        model.addAttribute("selected_employee_id", employee_id);
        return "contract-creating";
    }

    @PostMapping("/contract/create")
    public String createContract(@ModelAttribute FullContractInfo contractInfo) {
        contractService.save(contractInfo);
        return "contract-creating";
    }

    @GetMapping("/contract/{id}/report/create")
    public String getReportCreating(@PathVariable("id") UUID id,
                                    Model model) {
        model.addAttribute("contractId", id);
        return "report-creating";
    }

    @PostMapping("/contract/{id}/report/create")
    public String createReport(@PathVariable("id") UUID contractId,
                               @ModelAttribute WorkTimeReportInfo reportInfo) {
        reportService.save(contractId, reportInfo);
        return "redirect:/contract/{id}/report/create";
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ModelAndView handleException(HttpServletRequest req, NullPointerException exception) {
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("exception", exception.getClass());
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", exception.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {IllegalWorkTimeReportInfo.class})
    public ModelAndView handleException(HttpServletRequest req, IllegalWorkTimeReportInfo exception) {
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("exception", exception.getClass());
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", exception.getMessage());
        return mav;
    }
}
