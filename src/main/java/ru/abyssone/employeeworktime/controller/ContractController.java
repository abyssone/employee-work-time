package ru.abyssone.employeeworktime.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
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
import ru.abyssone.employeeworktime.service.util.exception.IllegalContractException;
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
        model.addAttribute("employees", employeeService.getAllGeneralEmployeeInfoIfContractExists());
        model.addAttribute("schedules", scheduleService.findAllAsScheduleDescription());
        model.addAttribute("selected_employee_id", employee_id);
        return "contract-creating";
    }

    @PostMapping("/contract/create")
    public String createContract(@ModelAttribute FullContractInfo contractInfo) {
        contractService.save(contractInfo);
        return "redirect:/contract/create";
    }

    @GetMapping("/contract/{id}/edit")
    public String getContractEditing(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("schedules", scheduleService.findAllAsScheduleDescription());
        model.addAttribute("contract", contractService.getFullContractInfo(id));
        return "contract-editing";
    }

    @PostMapping("/contract/{id}/edit")
    public String editContract(@PathVariable("id") UUID id,
                               @ModelAttribute FullContractInfo contractInfo) {
        contractInfo.setId(id);
        contractService.update(contractInfo);
        return "redirect:/";
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
}
