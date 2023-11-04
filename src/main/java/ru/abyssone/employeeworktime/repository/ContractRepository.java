package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.abyssone.employeeworktime.entity.Contract;

import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    @Query("SELECT contract FROM Contract contract LEFT JOIN FETCH contract.workTimeReports WHERE contract.id = :contractId")
    Optional<Contract> findByIdFetchWorkTimeReports(@Param("contractId") UUID contractId);
}
