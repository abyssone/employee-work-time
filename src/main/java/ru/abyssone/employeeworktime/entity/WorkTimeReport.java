package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.LocalDate;

/**
 * Отчет о проведенном сотрудником времени на рабочем месте за конкретную дату.
 */
@Entity
@Table(name = "work_time_reports")
@Getter
@Setter
@NoArgsConstructor
public class WorkTimeReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата
     */
    private LocalDate date;

    /**
     * Причина отсутствия
     */
    private AbsenceReason absenceReason;

    /**
     * Трудовой договор
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    /**
     * Фактические время работы
     */
    @Embedded
    private TimePeriod workedTime;
}
