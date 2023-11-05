package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exceptional_days")
@Getter
@Setter
@NoArgsConstructor
public class ExceptionalDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Embedded
    private TimePeriod workTime;
    private String info;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "contracts_to_exceptional_days",
            joinColumns = @JoinColumn(name = "exceptional_day_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id"))
    private Set<Contract> contracts = new HashSet<>();

    public void addContract(Contract contract) {
        if (this.contracts.contains(contract)) {
            return;
        }
        this.contracts.add(contract);
        contract.addExceptionalDay(this);
    }
}
