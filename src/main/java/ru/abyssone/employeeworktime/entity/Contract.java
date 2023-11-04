package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.LocalDate;
import java.util.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @Setter(AccessLevel.NONE) // Замена lombok сеттера для обеспечения связи сущностей
    private Employee employee;

    // todo: Поменять на LAZY
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    @MapKey(name = "date")
    private Map<LocalDate, WorkTimeReport> workTimeReports = new HashMap<>();

    private LocalDate dateOfConclusion;
    private LocalDate entryIntoForceDate;
    private LocalDate expirationDate;
    private String position;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private WorkTimeModel workTimeModel;

    public void setEmployee(Employee employee) {
        if (this.employee != null && this.employee.equals(employee)) {
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
