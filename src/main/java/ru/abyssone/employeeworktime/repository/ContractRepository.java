package ru.abyssone.employeeworktime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abyssone.employeeworktime.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}
