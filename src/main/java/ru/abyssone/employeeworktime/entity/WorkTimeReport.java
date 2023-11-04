package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.LocalDate;

@Entity
@Table(name = "work_time_reports")
@Getter
@Setter
@NoArgsConstructor
public class WorkTimeReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    @Embedded
    private TimePeriod workedTime;
}
