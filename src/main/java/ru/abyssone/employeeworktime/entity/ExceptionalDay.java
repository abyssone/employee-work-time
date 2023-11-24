package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Исключительный день. Используется для "точечного" изменения рабочего времени сотрудника в какой-либо
 * конкретный день без изменения самого графика работы сотрудника.
 * Пример использования: нерабочий праздничный день, "рабочая суббота" из-за переноса нерабочего праздничного
 * дня на другую дату
 */
@Entity
@Table(name = "exceptional_days")
@Getter
@Setter
@NoArgsConstructor
public class ExceptionalDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата
     */
    private LocalDate date;

    /**
     * Рабочее время
     */
    @Embedded
    private TimePeriod workTime;

    /**
     * Доп. информация
     */
    private String info;

    /**
     * Коллекция контрактов, на которую распространяется данный exeptionalDay
     */
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
