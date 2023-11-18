package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    /*
     * UUID используется для корректной работы equals и hashCode вновь созданных объектов,
     * еще не сохраненных в бд
     * */
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    // todo: возможно заменить на LAZY
    // Optional
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "employee")
    @Getter(AccessLevel.NONE)
    private Contract contract;
    //private Optional<Account> account;

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
