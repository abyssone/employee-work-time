package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

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
    Optional<Contract> findByIdFetchReports(@Param("contractId") UUID contractId);

    @Query("SELECT contract FROM Contract contract WHERE contract.id IN :contractIdList")
    List<Contract> findByIdList(@Param("contractIdList") List<UUID> contracts);

    @Modifying
    @Query("UPDATE Contract c SET c.position = :#{#contract.position}, c.workTimeModel = :#{#contract.workTimeModel}, " +
            "c.dateOfConclusion = :#{#contract.dateOfConclusion}, c.expirationDate = :#{#contract.expirationDate.orElse(null)} " +
            "WHERE c.id = :#{#contract.id}")
    void updateIgnoreEmployee(@Param("contract") Contract contract);
}
