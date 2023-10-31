package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<Contract> contracts = new HashSet<>();
    //private Optional<Account> account;

    public void addContract(Contract contract) {
        if (this.contracts.contains(contract)) {
            return;
        }
        this.contracts.add(contract);
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
