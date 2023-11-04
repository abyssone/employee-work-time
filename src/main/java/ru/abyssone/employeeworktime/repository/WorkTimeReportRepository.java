package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abyssone.employeeworktime.entity.WorkTimeReport;

public interface WorkTimeReportRepository extends JpaRepository<WorkTimeReport, Long> {
}
