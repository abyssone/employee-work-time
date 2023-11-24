package ru.abyssone.employeeworktime.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.abyssone.employeeworktime.dto.DailyWorkReport;
import ru.abyssone.employeeworktime.service.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class Reports {

    private final ReportService reportService;
    @GetMapping("/api/statistic")
    public List<DailyWorkReport> getDailyWorkReports(@RequestParam("contractId") UUID contractId,
                                                     @RequestParam("startDate") String startDate,
                                                     @RequestParam("endDate") String endDate) {

        List<DailyWorkReport> dailyWorkReports = reportService
                .getDailyWorkReports(contractId, startDate, endDate);
        return dailyWorkReports;
    }
}
