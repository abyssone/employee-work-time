package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abyssone.employeeworktime.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
