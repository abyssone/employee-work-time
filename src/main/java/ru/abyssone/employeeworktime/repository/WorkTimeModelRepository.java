package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.util.UUID;

public interface WorkTimeModelRepository extends JpaRepository<WorkTimeModel, UUID> {
}
