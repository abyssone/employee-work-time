package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.abyssone.employeeworktime.entity.WorkTimeReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WorkTimeReportRepository extends JpaRepository<WorkTimeReport, Long> {

    @Query("SELECT report FROM Contract contract LEFT JOIN contract.workTimeReports report " +
            "WHERE contract.id = :contractId AND report.date BETWEEN :startDate AND :endDate")
    List<WorkTimeReport> findReportsByIdAndDatePeriod(@Param("contractId") UUID contractId,
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);
}
