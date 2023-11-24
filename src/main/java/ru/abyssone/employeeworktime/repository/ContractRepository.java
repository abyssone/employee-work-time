package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    @Query("SELECT contract FROM Contract contract LEFT JOIN FETCH contract.exceptionalDays")
    List<Contract> findAllFetchExceptionalDays();

    @Query("SELECT contract.workTimeModel FROM Contract contract WHERE contract.id = :contractId")
    WorkTimeModel findByIdWorkTimeModel(@Param("contractId") UUID contractId);

    @Query("SELECT contract FROM Contract contract LEFT JOIN FETCH contract.workTimeReports report " +
            "WHERE contract.id = :contractId")
    Optional<Contract> findByIdFetchReportByDate(@Param("contractId") UUID contractId);
}
