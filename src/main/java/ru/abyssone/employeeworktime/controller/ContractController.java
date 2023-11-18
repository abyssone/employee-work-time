package ru.abyssone.employeeworktime.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.abyssone.employeeworktime.dto.FullContractInfo;
import ru.abyssone.employeeworktime.dto.ScheduleType;
import ru.abyssone.employeeworktime.service.ContractService;
import ru.abyssone.employeeworktime.service.EmployeeService;
import ru.abyssone.employeeworktime.service.ScheduleService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ContractController {

    private final EmployeeService employeeService;
    private final ScheduleService scheduleService;
    private final ContractService contractService;

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
}
