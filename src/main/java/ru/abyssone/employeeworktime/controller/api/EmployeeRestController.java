package ru.abyssone.employeeworktime.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.abyssone.employeeworktime.dto.employee.GeneralEmployeeInfo;
import ru.abyssone.employeeworktime.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping("/api/employee/all-info")
    public List<GeneralEmployeeInfo> getFilteredAndSortedEmployees (@RequestParam("search") String searchString,
                                                                    @RequestParam("sort") String sortString) {
        List<GeneralEmployeeInfo> empInfo = employeeService
                .getFilteredAndSortedEmployeesInfo(searchString, sortString);

        return empInfo;
    }
}
