package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.LocalDate;
import java.util.*;

/**
 * Трудовой договор
 */
@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
public class Contract {

    /*
    * UUID используется для корректной работы equals и hashCode вновь созданных объектов,
    * еще не сохраненных в бд
    * */
    @Id
    private UUID id = UUID.randomUUID();

    /**
     * Сотрудник, с которым заключен контракт
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @Setter(AccessLevel.NONE) // Замена lombok сеттера для обеспечения связи сущностей
    private Employee employee;

    /**
     * Отчеты о фактическом времени работы сотрудника за определенную дату
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contract")
    @MapKey(name = "date")
    private Map<LocalDate, WorkTimeReport> workTimeReports = new HashMap<>();

    /**
     * Дни, вносящие изменения в общий график работы (например, праздники)
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "contracts")
    @MapKey(name = "date")
    private Map<LocalDate, ExceptionalDay> exceptionalDays = new HashMap<>();

    /**
     * Дата заключения трудового договора
     */
    private LocalDate dateOfConclusion;

    /**
     * (Optional) Дата завершения срока действия договора, если договор является временным
     */
    @Getter(AccessLevel.NONE)
    private LocalDate expirationDate;

    /**
     * Должность
     */
    private String position;

    /**
     * График работы
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private WorkTimeModel workTimeModel;

    public void setEmployee(Employee employee) {
        if (employee == null || (this.employee != null && this.employee.equals(employee))) {
            return;
        }
        this.employee = employee;
        employee.addContract(this);
    }

    public void addWorkTimeReport(WorkTimeReport workTimeReport) {
        if (this.workTimeReports.containsKey(workTimeReport.getDate())) {
            return;
        }
        this.workTimeReports.put(workTimeReport.getDate(), workTimeReport);
    }

    public Optional<LocalDate> getExpirationDate() {
        if (this.expirationDate == null) return Optional.empty();
        return Optional.of(this.expirationDate);
    }

    public void addExceptionalDay(ExceptionalDay exceptionalDay) {
        if (this.exceptionalDays.containsKey(exceptionalDay.getDate())) {
            return;
        }
        this.exceptionalDays.put(exceptionalDay.getDate(), exceptionalDay);
        exceptionalDay.addContract(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return Objects.equals(id, contract.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
