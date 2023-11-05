package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abyssone.employeeworktime.entity.ExceptionalDay;

public interface ExceptionalDayRepository extends JpaRepository<ExceptionalDay, Long> {
}
