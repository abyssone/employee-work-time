package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

/**
 * Сотрудник, предоставляет общую информацию
 */
@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    /**
     * Пол
     */
    public enum Sex {
        MALE, FEMALE
    }

    /*
     * UUID используется для корректной работы equals и hashCode вновь созданных объектов,
     * еще не сохраненных в бд
     * */
    @Id
    private UUID id = UUID.randomUUID();

    /**
     * Имя
     */
    private String name;

    /**
     * Адрес
     */
    private String address;

    /**
     * Дата рождения
     */
    private LocalDate BirthDate;

    /**
     * Аккаунт пользователя
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "employee")
    private Account account;

    /**
     * Пол
     */
    private Sex sex;

    /**
     * (Optional) Трудовой договор сотрудника
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employee")
    @Getter(AccessLevel.NONE)
    private Contract contract;

    public Optional<Contract> getContract() {
        if (this.contract == null) return Optional.empty();
        return Optional.of(this.contract);
    }

    public void addContract(Contract contract) {
        if (contract == null || (this.contract != null && this.contract.equals(contract))) {
            return;
        }
        this.contract = contract;
        contract.setEmployee(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
