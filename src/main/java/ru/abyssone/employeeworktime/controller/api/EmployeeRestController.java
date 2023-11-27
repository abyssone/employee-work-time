package ru.abyssone.employeeworktime.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.abyssone.employeeworktime.dto.employee.EmployeeId;
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

    @PostMapping(value = "/api/employee/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteEmployee(@RequestBody EmployeeId employeeId) {
        employeeService.delete(employeeId);
        return new ModelAndView("redirect:/");
    }
}
