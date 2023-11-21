package ru.abyssone.employeeworktime.entity.timemodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Класс абстрактной сущности "график работы". Необходим для реализации различных графиков работы,
 * их хранения в базе данных и использования абстрактного типа в других сущностях.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
public abstract class WorkTimeModel {

    @Id
    private UUID id = UUID.randomUUID();

    private String title;

    // Для dto и работы Query в repository
    public WorkTimeModel(UUID id, String title) {
        this.id = id;
        this.title = title;
    }
}
