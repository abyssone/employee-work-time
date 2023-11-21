package ru.abyssone.employeeworktime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.dto.DailyWorkReport;
import ru.abyssone.employeeworktime.entity.ExceptionalDay;
import ru.abyssone.employeeworktime.entity.WorkTimeReport;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;
import ru.abyssone.employeeworktime.mapper.ReportMapper;
import ru.abyssone.employeeworktime.repository.ContractRepository;
import ru.abyssone.employeeworktime.repository.ExceptionalDayRepository;
import ru.abyssone.employeeworktime.repository.WorkTimeReportRepository;
import ru.abyssone.employeeworktime.service.timemodel.WorkTimeModelService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ContractRepository contractRepository;
    private final ExceptionalDayRepository exceptionalDayRepository;
    private final WorkTimeModelService workTimeModelService;
    private final WorkTimeReportRepository workTimeReportRepository;
    private final ReportMapper reportMapper;
    public List<DailyWorkReport> getDailyWorkReports(UUID contractId, LocalDate startDate, LocalDate endDate) {

        List<ExceptionalDay> exDays = exceptionalDayRepository
                .findExceptionalDaysByContractId(contractId, startDate, endDate);

        List<WorkTimeReport> reportsList = workTimeReportRepository
                .findReportsByIdAndDatePeriod(contractId, startDate, endDate);

        Map<LocalDate, WorkTimeReport> reports = reportsList.stream()
                .collect(Collectors.toMap(WorkTimeReport::getDate, report -> report));

        WorkTimeModel schedule = contractRepository.findByIdWorkTimeModel(contractId);

        Map<LocalDate, TimePeriod> scheduledWorkTime = workTimeModelService
                .getWorkHoursForDatePeriod(schedule, startDate, endDate.plusDays(1));

        // Переопределение рабочего времени для дней, для которых существует ExceptionalDay TimePeriod
        for (ExceptionalDay exDay : exDays) {
            scheduledWorkTime.put(exDay.getDate(), exDay.getWorkTime());
        }

        return reportMapper.toDailyWorkReports(scheduledWorkTime, reports);
    }
}
