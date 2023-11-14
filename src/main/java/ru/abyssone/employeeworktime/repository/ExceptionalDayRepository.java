package ru.abyssone.employeeworktime.repository;

import ru.abyssone.employeeworktime.entity.ExceptionalDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionalDayRepository extends JpaRepository<ExceptionalDay, Long> {
}
