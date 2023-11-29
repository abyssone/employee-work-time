package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.abyssone.employeeworktime.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.abyssone.employeeworktime.repository.custom.CustomEmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>, CustomEmployeeRepository {

    @Query("SELECT employee FROM Employee employee LEFT JOIN FETCH employee.contract WHERE employee.id = :id")
    Optional<Employee> findByIdFetchContract(@Param("id") UUID id);

    @Query("SELECT employee FROM Employee employee LEFT JOIN FETCH employee.contract contract " +
            "WHERE contract IS NOT NULL")
    List<Employee> findAllWithContract();

    @Query("SELECT employee FROM Employee employee LEFT JOIN FETCH employee.contract contract " +
            "WHERE contract IS NULL")
    List<Employee> findAllWithoutContract();
}
