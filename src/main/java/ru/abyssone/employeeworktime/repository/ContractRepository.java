package ru.abyssone.employeeworktime.repository;

import ru.abyssone.employeeworktime.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    @Query("SELECT contract FROM Contract contract LEFT JOIN FETCH contract.workTimeReports WHERE contract.id = :contractId")
    Optional<Contract> findByIdFetchWorkTimeReports(@Param("contractId") UUID contractId);

    @Query("SELECT contract FROM Contract contract LEFT JOIN FETCH contract.exceptionalDays")
    List<Contract> findAllFetchWorkTimeReports();
}
