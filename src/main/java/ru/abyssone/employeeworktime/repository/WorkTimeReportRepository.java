package ru.abyssone.employeeworktime.repository;

import ru.abyssone.employeeworktime.entity.WorkTimeReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTimeReportRepository extends JpaRepository<WorkTimeReport, Long> {
}
