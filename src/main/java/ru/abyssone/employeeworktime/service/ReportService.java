package ru.abyssone.employeeworktime.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abyssone.employeeworktime.dto.report.FullWorkReportsStatistic;
import ru.abyssone.employeeworktime.dto.report.WorkTimeReportInfo;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.ExceptionalDay;
import ru.abyssone.employeeworktime.entity.WorkTimeReport;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;
import ru.abyssone.employeeworktime.mapper.ReportMapper;
import ru.abyssone.employeeworktime.repository.ContractRepository;
import ru.abyssone.employeeworktime.repository.ExceptionalDayRepository;
import ru.abyssone.employeeworktime.repository.WorkTimeReportRepository;
import ru.abyssone.employeeworktime.service.timemodel.WorkTimeModelService;
import ru.abyssone.employeeworktime.service.util.Validator;
import ru.abyssone.employeeworktime.service.util.exception.IllegalWorkTimeReportInfo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ContractRepository contractRepository;
    private final ExceptionalDayRepository exceptionalDayRepository;
    private final WorkTimeModelService workTimeModelService;
    private final WorkTimeReportRepository workTimeReportRepository;
    private final ReportMapper reportMapper;
    private final Validator validator;

    // Т.к. save выполняется с помощью native sql, то метод должен быть transactional
    @Transactional
    public void save(UUID contractId, WorkTimeReportInfo reportInfo) throws IllegalWorkTimeReportInfo,
                                                                            NullPointerException {
        try {
            validator.check(reportInfo);
        } catch (IllegalWorkTimeReportInfo ex) {
            log.error(ex.getMessage());
            throw ex;
        }

        WorkTimeReport workTimeReport = reportMapper.toWorkTimeReport(reportInfo);

        Optional<Contract> contract = contractRepository.findByIdFetchReports(contractId);
        if (contract.isEmpty()) {
            String msg = String.format("Contract with id: %s not found", contractId);
            log.error(msg);
            throw new NullPointerException(msg);
        }

        // Если отчет за указанную дату уже существует
        if (contract.get().getWorkTimeReports().containsKey(workTimeReport.getDate())) {
            String msg = String.format("Contract with id: %s already contains report for %s",
                    contract.get().getId(), workTimeReport.getDate().toString());
            log.error(msg);
            throw new IllegalWorkTimeReportInfo(msg);
        }

        workTimeReportRepository.save(contractId, workTimeReport);
    }

    /*
     * Возвращает полную статистику по рабочему времени по графику и фактически отработанному времени
     * за указанный промежуток дат
     */
    public FullWorkReportsStatistic getFullWorkReportsStatistic(UUID contractId,
                                                                String startDateString,
                                                                String endDateString) {

        if (startDateString.isEmpty() || endDateString.isEmpty()) return null;

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(startDateString);
            endDate = LocalDate.parse(endDateString);
        } catch (DateTimeParseException exception) {
            log.error(exception.getClass() + exception.getMessage());
            return null;
        }

        Optional<Contract> contract = contractRepository.findById(contractId);
        if (contract.isEmpty()) throw new NullPointerException(String.format(
            "contract with id %s not found", contractId
        ));

        // Если указанный период выходит за рамки действия контракта, то период корректируется
        if (startDate.isBefore(contract.get().getDateOfConclusion())) startDate = contract.get().getDateOfConclusion();
        if (contract.get().getExpirationDate().isPresent()) {
            if (endDate.isAfter(contract.get().getExpirationDate().get())) {
                endDate = contract.get().getExpirationDate().get();
            }
        }

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

        return reportMapper.toFullWorkReportsStatistic(scheduledWorkTime, reports);
    }
}
