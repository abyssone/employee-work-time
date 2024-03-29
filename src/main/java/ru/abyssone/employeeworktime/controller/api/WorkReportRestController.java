package ru.abyssone.employeeworktime.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.abyssone.employeeworktime.dto.report.FullWorkReportsStatistic;
import ru.abyssone.employeeworktime.service.ReportService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WorkReportRestController {

    private final ReportService reportService;
    @GetMapping("/api/statistic")
    public FullWorkReportsStatistic getDailyWorkReports(@RequestParam("contractId") UUID contractId,
                                                        @RequestParam("startDate") String startDate,
                                                        @RequestParam("endDate") String endDate) {

        return reportService.getFullWorkReportsStatistic(contractId, startDate, endDate);
    }
}
