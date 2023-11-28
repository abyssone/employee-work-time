package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.abyssone.employeeworktime.entity.ExceptionalDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExceptionalDayRepository extends JpaRepository<ExceptionalDay, Long> {

    @Query("SELECT exDays FROM ExceptionalDay exDays LEFT JOIN exDays.contracts contract " +
            "WHERE contract.id = :contractId AND exDays.date BETWEEN :startDate AND :endDate")
    List<ExceptionalDay> findExceptionalDaysByContractId(@Param("contractId") UUID contractId,
                                                         @Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);


    @Query("SELECT exDay FROM ExceptionalDay exDay LEFT JOIN FETCH exDay.contracts WHERE " +
            "exDay.id = :id")
    Optional<ExceptionalDay> findByIdFetchContracts(@Param("id") Long id);
}
