package ru.abyssone.employeeworktime.entity.timemodel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FixedWorkWeek extends WorkTimeModel{

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "week_day_work_hours_mapping",
            joinColumns = {@JoinColumn(name = "fixed_work_week_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day_of_week")
    private Map<DayOfWeek, TimePeriod> workHours;

    {
        workHours = new LinkedHashMap<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            workHours.put(dayOfWeek, null);
        }
    }
}
