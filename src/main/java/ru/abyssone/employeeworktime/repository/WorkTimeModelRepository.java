package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.util.List;
import java.util.UUID;

public interface WorkTimeModelRepository extends JpaRepository<WorkTimeModel, UUID> {

    @Query(value = "SELECT model.id, model.title, model.clazz FROM " +
            "(SELECT id, title, 'FIXED_WORK_WEEK' as clazz FROM fixed_work_week" +
            " UNION ALL SELECT id, title, 'SHIFT_WORK_SCHEDULE' as clazz FROM shift_work_schedule) model", nativeQuery = true)
    List<WorkTimeModelObject> findAllAsIdAndName();
}
