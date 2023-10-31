package ru.abyssone.employeeworktime.entity.timemodel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
public abstract class WorkTimeModel {

    @Id
    private UUID id = UUID.randomUUID();

    public abstract Integer getWorkHours(LocalDate date);
    public abstract Map<LocalDate, Integer> getWorkHours(LocalDate start, LocalDate end);
}
