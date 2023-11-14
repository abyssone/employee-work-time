package ru.abyssone.employeeworktime.repository;

import ru.abyssone.employeeworktime.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
